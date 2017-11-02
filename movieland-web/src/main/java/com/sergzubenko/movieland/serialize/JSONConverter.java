package com.sergzubenko.movieland.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JSONConverter {

    public String getJson(Object object, String columns) {

        SimpleBeanPropertyFilter filter;

        if (!"".equals(columns)) {
            filter = SimpleBeanPropertyFilter.serializeAllExcept(columns.split(","));
        } else {
            filter = SimpleBeanPropertyFilter.serializeAll();
        }

        System.out.println(filter);

        FilterProvider filters = new SimpleFilterProvider().addFilter("allMoviesFilter", filter);
        try {
            return (new ObjectMapper())
                    .writer(filters)
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
