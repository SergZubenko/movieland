package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.persistance.api.GenreDao;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class CachedGenreDao implements GenreDao {

    private volatile List<Genre> cache;

    @Resource(name = "jdbcGenreDao")
    private GenreDao genreDao;

    @Override
    public void enrichMovies(List<Movie> movies) {
        genreDao.enrichMovies(movies);
    }

    @Override
    public void enrichMovie(Movie movie) {
        genreDao.enrichMovie(movie);
    }

    @Override
    public List<Genre> getAll() {
        return new ArrayList<>(cache);
    }

    @PostConstruct
    @Scheduled(fixedDelayString = "${dao.genre.cacheUpdateInterval}", initialDelayString = "${dao.genre.cacheUpdateInterval}")
    private void updateCache() {
        cache = genreDao.getAll();
    }
}
