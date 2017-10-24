package com.sergzubenko.data.utils;

import com.sergzubenko.data.repository.Movie;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sergz on 24.10.2017.
 */
public class SortedColumnMapperTest {
    SortedColumnMapper sortedColumnMapper = new SortedColumnMapper();

    @Test
    public void getColumnName() throws Exception {

        assertEquals("price", sortedColumnMapper.getColumnName(Movie.class,"price"));
        assertEquals("rating", sortedColumnMapper.getColumnName(Movie.class,"rating"));
    }

    @Test(expected = NoSuchFieldError.class)
    public void getColumnNameError() throws Exception {
        assertEquals("price", sortedColumnMapper.getColumnName(Movie.class,"no_price"));
    }

}