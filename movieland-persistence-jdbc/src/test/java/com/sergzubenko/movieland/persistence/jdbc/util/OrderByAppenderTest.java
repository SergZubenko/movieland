package com.sergzubenko.movieland.persistence.jdbc.util;


import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OrderByAppenderTest {
    private OrderByAppender orderByBuilder = new OrderByAppender();

    @Test
    public void getColumnName() throws Exception {
        assertEquals("price", orderByBuilder.getColumnName(TestSorted.class, "price"));
        assertEquals("rating", orderByBuilder.getColumnName(TestSorted.class, "rating"));
    }

    @Test(expected = NoSuchFieldError.class)
    public void getColumnNameError() throws Exception {
        assertEquals("price", orderByBuilder.getColumnName(TestSorted.class, "no_price"));
    }

    static class TestSorted {
        @Sorted("extraprice")
        double price;
        @Sorted
        double rating;
    }


    @Test
    public void prepareOrderedQuery() throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("extraprice", "asc");
        params.put("rating", "desc");

        assertEquals(" order by price ASC, rating DESC",
                orderByBuilder.prepareOrderedQuery("", "order by", TestSorted.class, params));

    }

}


