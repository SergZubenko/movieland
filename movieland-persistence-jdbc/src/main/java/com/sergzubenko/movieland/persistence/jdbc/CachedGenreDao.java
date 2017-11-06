package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.persistance.api.GenreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class CachedGenreDao {

    private List<Genre> cache;

    private ReentrantReadWriteLock cacheLock;

    @Autowired
    private GenreDao genreDao;

    public CachedGenreDao() {
        cacheLock = new ReentrantReadWriteLock(true);
    }

    @PostConstruct
    @Scheduled(fixedDelayString = "${dao.genre.cacheUpdateInterval}", initialDelayString = "${dao.genre.cacheUpdateInterval}")
    private void updateCache() {
        List<Genre> updatedCache = genreDao.getGenres();
        cacheLock.writeLock().lock();
        try {
            cache = updatedCache;
        } finally {
            cacheLock.writeLock().unlock();
        }
    }

    public List<Genre> getGenres() {
        List<Genre> copyCache;
        try {
            cacheLock.readLock().lock();
            copyCache = new ArrayList<>(cache);
        } finally {
            cacheLock.readLock().unlock();
        }
        return copyCache;
    }
}
