package com.sergzubenko.data.dao;

import com.sergzubenko.data.repository.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by sergz on 24.10.2017.
 */
@Component("cached")
@Lazy(false)
public class GenreCachedDao implements GenreDao {

    @Autowired(required = true)
    @Qualifier("JdbcGenreDao")
    GenreDao dao;

    List<Genre> cache;

    ScheduledExecutorService executorService;

    @PostConstruct
    private void initTimer(){
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::updateCache,
                0,
                4,
                 TimeUnit.HOURS);
    }

    private synchronized void updateCache(){
        cache = dao.getGenres();
    }

    private synchronized List<Genre> getData(){
        return cache;
    }

    @Override
    public List<Genre> getGenres() {
        return getData();
    }



}
