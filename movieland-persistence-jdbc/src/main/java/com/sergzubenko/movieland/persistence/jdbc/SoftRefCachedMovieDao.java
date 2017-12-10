package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Primary
@Profile("softRefCache")
public class SoftRefCachedMovieDao implements MovieDao {
    private static final Logger log = LoggerFactory.getLogger(SoftRefCachedMovieDao.class);
    private Map<Integer, SoftReference<Movie>> cache;

    @Autowired
    private MovieDao movieDao;

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
        log.info("Retrieving movie {} from cache", id);
        Movie movie;
        SoftReference<Movie> refMovie = cache.get(id);
        if (refMovie != null && (movie = refMovie.get()) != null) {
            return  new Movie(movie);
        }
        log.info("Movie {} not fount in cache. Retrieving from database", id);
        movie = movieDao.getMovieById(id);
        log.info("Movie not fount in cache. Retrieving from database");
        cache.put(movie.getId(), new SoftReference<>(movie));
        return new Movie(movie);
    }

    @Override
    public void persist(Movie movie) {
        movieDao.persist(movie);
        Movie copyMovie = new Movie(movie);
        cache.put(copyMovie.getId(), new SoftReference<>(copyMovie));
    }

    @PostConstruct
    private void init() {
        log.info("Initializing SoftReference movie cache");
        cache = new ConcurrentHashMap<>();
    }


}
