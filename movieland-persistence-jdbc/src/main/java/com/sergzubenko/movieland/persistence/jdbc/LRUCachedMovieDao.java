package com.sergzubenko.movieland.persistence.jdbc;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Primary
@Profile("LRUCachingType")
public class LRUCachedMovieDao implements MovieDao {
    private static final Logger log = LoggerFactory.getLogger(LRUCachedMovieDao.class);
    private volatile Cache<Integer, Movie> cache;

    @Autowired
    private MovieDao movieDao;

    @Value("${movie.cache.maxSize}")
    long maxSize;

    @Override
    public List<Movie> getRandomMovies() {
        return movieDao.getRandomMovies();
    }

    @Override
    public List<Movie> getMoviesByGenre(Integer genreId, Map<String, String> params) {
        return movieDao.getMoviesByGenre(genreId, params);
    }

    @Override
    public List<Movie> getAll(Map<String, String> params) {
        return movieDao.getAll(params);
    }

    @Override
    public Movie getMovieById(Integer id) {
        log.info("Retrieving movie {} from cache. Add if not found", id);
        try {
            return new Movie(cache.get(id, () -> movieDao.getMovieById(id)));
        } catch (ExecutionException e) {
            log.error("error during retrieving movie from database\n{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void persist(Movie movie) {
        movieDao.persist(movie);
        Movie copyMovie = new Movie(movie);
        cache.put(copyMovie.getId(), copyMovie);
    }

    @PostConstruct
    private void init() {
        log.info("Initializing LRU movie cache \n" +
                "size:{}", maxSize);

        cache = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .build();
    }
}
