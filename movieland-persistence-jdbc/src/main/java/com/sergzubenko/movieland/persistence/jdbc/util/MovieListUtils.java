package com.sergzubenko.movieland.persistence.jdbc.util;

import com.sergzubenko.movieland.entity.Movie;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieListUtils {

    public static Map<String, ?> getIds(List<Movie> entities){
        List<Integer> ids = entities.stream().map(Movie::getId).collect(Collectors.toList());
        return Collections.singletonMap("ids", ids);
    }

    public static Movie findInListById(Integer id, List<Movie> entities) {
        return entities.stream().filter(m -> m.getId()
                .equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unexpected movie ID found : " + id));
    }


}