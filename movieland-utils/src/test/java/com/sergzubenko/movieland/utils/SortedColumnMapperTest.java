package com.sergzubenko.movieland.utils;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SortedColumnMapperTest {
    private SortedColumnMapper sortedColumnMapper = new SortedColumnMapper();

    @Test
    public void getColumnName() throws Exception {
        assertEquals("price", sortedColumnMapper.getColumnName(TestSorted.class,"price"));
        assertEquals("rating", sortedColumnMapper.getColumnName(TestSorted.class,"rating"));
    }

    @Test(expected = NoSuchFieldError.class)
    public void getColumnNameError() throws Exception {
        assertEquals("price", sortedColumnMapper.getColumnName(TestSorted.class,"no_price"));
    }

    static class TestSorted{

        @Sorted
        double price;
        @Sorted
        double rating;
    }

}


