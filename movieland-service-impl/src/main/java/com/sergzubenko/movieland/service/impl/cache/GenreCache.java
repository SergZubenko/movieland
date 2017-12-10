package com.sergzubenko.movieland.service.impl.cache;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.persistance.api.GenreDao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenreCache{
    private volatile List<Genre> cache;

    @Resource(name = "jdbcGenreDao")
    private GenreDao genreDao;

    public List<Genre> getAll() {
        return new ArrayList<>(cache);
    }

    @PostConstruct
    @Scheduled(fixedDelayString = "${dao.genre.cacheUpdateInterval}", initialDelayString = "${dao.genre.cacheUpdateInterval}")
    private void updateCache() {
        cache = genreDao.getAll();
    }
}
