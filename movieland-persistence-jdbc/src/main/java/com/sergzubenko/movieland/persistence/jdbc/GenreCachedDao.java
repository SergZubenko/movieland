package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.persistance.api.GenreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class GenreCachedDao {

    private List<Genre> cache;

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private GenreDao genreDao;

    private ReentrantReadWriteLock cacheLock;

    public GenreCachedDao() {
        cacheLock = new ReentrantReadWriteLock();
    }

    @Scheduled(cron = "${dao.genre.cacheUpdateInterval}")
    private void updateCache() {
        List<Genre> updatedCache = genreDao.getGenres();
        cacheLock.writeLock().lock();
        try {
            cache = updatedCache;
        } finally {
            cacheLock.writeLock().unlock();
        }
    }

    private void checkUpdateCache() {
        if (cache == null) {
            updateCache();
        }
    }

    private List<Genre> getData() {
        List<Genre> copyCache;
        checkUpdateCache();
        try {
            cacheLock.readLock().lock();
            copyCache = new ArrayList<>(cache);
        } finally {
            cacheLock.readLock().unlock();
        }
        return copyCache;
    }

    public List<Genre> getGenres() {
        return getData();
    }

   }
