package com.sergzubenko.movieland.persistence.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext
public class JdbcMovieDaoTest {

    @Autowired
    JdbcMovieDao jdbcMovieDao;

    @Value("${sql.movie.allMovies}")
    private String getAllMoviesSQL;


    @Value("${sql.common.orderBy}")
    private String orderBySQL;

    @Test
    public void prepareOrderedQuery() throws Exception {
        Map<String, String> params  = new HashMap<>();
        params.put("price","asc");
        params.put("rating", "desc");

        String query =  jdbcMovieDao.prepareOrderedQuery(params);

        System.out.println(query);

        assertEquals(getAllMoviesSQL + " "+orderBySQL + " price ASC,rating DESC",
                query);

    }

}