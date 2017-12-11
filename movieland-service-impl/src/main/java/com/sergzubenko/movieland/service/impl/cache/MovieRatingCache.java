package com.sergzubenko.movieland.service.impl.cache;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.entity.MovieRating;
import com.sergzubenko.movieland.entity.MovieUserVote;
import com.sergzubenko.movieland.persistance.api.MovieRatingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
@ManagedResource(objectName = "com.sergzubenko.movieland.jmx:name=MovieRatingCache",
        description = "MovieRatingCache")
public class MovieRatingCache {
    private static final Logger log = LoggerFactory.getLogger(MovieRatingCache.class);

    @Autowired
    private MovieRatingDao movieRatingDao;

    private volatile Map<Integer, MovieRating> ratingCache;
    private volatile Queue<MovieUserVote> votesCache;

    @PostConstruct
    private void init() {
        votesCache = new ConcurrentLinkedQueue<>();
        ratingCache = new ConcurrentHashMap<>();
        log.info("votes cache and rating cache initialized");
    }

    @ManagedOperation
    public void resetCache() {
        flushBuffer();
        ratingCache = new ConcurrentHashMap<>();
        log.warn("Buffers were cleared");
    }

    public void rateMovie(Integer userId, Integer movieId, double rating) {
        cacheVote(userId, movieId, rating);
        updateMovieRating(movieId, rating);
        log.info("User {} rates movie {} wit value {}", userId, movieId, rating);
    }

    public void enrichMovie(Movie movie) {
        MovieRating movieRating = getLoadRating(movie.getId());
        log.info("found rating {}/{} for movie {}", movie.getId(), movieRating.getVotes(), movieRating.getRating());
        if (movieRating.getVotes() > 0) {
            movie.setRating(round(movieRating.getRating()));
            log.info("movie is enriched with value {} ", movieRating.getRating());
        }
    }

    public void enrichMovies(List<Movie> movies) {
        log.info("started enrichment for {} movies", movies.size());
        List<Integer> ids = movies.stream().map(Movie::getId).collect(Collectors.toList());
        checkLoadRating(ids);
        movies.forEach(this::enrichMovie);
        log.info("ended enrichment for {} movies", movies.size());
    }

    @Scheduled(fixedDelayString = "${movie.rating.flushBufferInterval}", initialDelayString = "${movie.rating.flushBufferInterval}")
    private void flushBuffer() {
        if (votesCache.size() == 0) {
            return;
        }
        List<MovieUserVote> votesToSave = new ArrayList<>(votesCache.size());
        MovieUserVote vote;
        while ((vote = votesCache.remove()) != null) {
            votesToSave.add(vote);
        }
        movieRatingDao.persistVotes(votesToSave);
    }

    private void updateMovieRating(Integer movieId, double rating) {
        MovieRating movieRating = getLoadRating(movieId);
        log.info("updating rating for movie {}", movieId);
        synchronized (movieRating) {
            movieRating.setVotes(movieRating.getVotes() + 1);
            double newRating = (movieRating.getRating() + rating) / movieRating.getVotes();
            movieRating.setRating(newRating);
            log.info("new value {}/{}", rating, movieRating.getVotes());
        }
    }

    private double round(double rating) {
        return BigDecimal.valueOf(rating).round(new MathContext(2)).doubleValue();
    }

    private MovieRating getLoadRating(Integer movieId) {
        return ratingCache.computeIfAbsent(movieId, (id) -> movieRatingDao.getRating(id).orElse(new MovieRating()));
    }

    private void checkLoadRating(List<Integer> movieIds) {
        List<Integer> idsToAdd = new ArrayList<>();
        for (Integer movieId : movieIds) {
            if (!ratingCache.containsKey(movieId)) {
                idsToAdd.add(movieId);
            }
        }
        List<MovieRating> ratings = movieRatingDao.getRating(idsToAdd);
        ratings.forEach(rating -> ratingCache.putIfAbsent(rating.getMovieId(), rating));
    }

    private void cacheVote(Integer userId, Integer movieId, double rating) {
        MovieUserVote userVote = new MovieUserVote();
        userVote.setRating(rating);
        userVote.setUserId(userId);
        userVote.setMovieId(movieId);
        votesCache.add(userVote);
    }
}
