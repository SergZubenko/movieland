package com.sergzubenko.movieland.service.impl.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import com.sergzubenko.movieland.service.api.cache.MovieCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

@Service
@Profile("default")
@ManagedResource(objectName = "com.sergzubenko.movieland.jmx:name=LRUMovieCache",
        description = "LRUMovieCache")
public class LRUMovieCache implements MovieCache {
    private static final Logger log = LoggerFactory.getLogger(LRUMovieCache.class);
    private volatile Cache<Integer, Movie> cache;

    @Autowired
    private MovieDao movieDao;

    @Value("${movie.cache.maxSize}")
    long maxSize;

    @Override
    public Movie getFromCache(Integer id) {
        log.info("Retrieving movie {} from cache. Add if not found", id);
        try {
            return new Movie(cache.get(id, () -> movieDao.getMovieById(id)));
        } catch (ExecutionException e) {
            log.error("error during retrieving movie from database\n{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addToCache(Movie movie) {
        Movie copyMovie = new Movie(movie);
        cache.put(copyMovie.getId(), copyMovie);
    }

    @ManagedOperation
    public void resetCache() {
        cache.cleanUp();
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
