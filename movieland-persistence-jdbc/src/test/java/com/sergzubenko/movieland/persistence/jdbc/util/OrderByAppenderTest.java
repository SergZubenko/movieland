package com.sergzubenko.movieland.persistence.jdbc.util;


import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OrderByAppenderTest {

    @Test
    public void getColumnName() throws Exception {
        assertEquals("price", OrderByAppender.getColumnName(TestSorted.class, "price"));
        assertEquals("rating", OrderByAppender.getColumnName(TestSorted.class, "rating"));
    }

    @Test(expected = NoSuchFieldError.class)
    public void getColumnNameError() throws Exception {
        assertEquals("price", OrderByAppender.getColumnName(TestSorted.class, "no_price"));
    }

    private static class TestSorted {
        @Sorted("extraprice")
        double price;
        @Sorted
        double rating;

        @Sorted("sdfsdfs")
        double someId;
    }


    @Test
    public void prepareOrderedQuery() throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("extraprice", "asc");
        params.put("rating", "desc");

        assertEquals(" order by price ASC, rating DESC",
                OrderByAppender.prepareOrderedQuery("", "order by", TestSorted.class, params));

    }

}


