package com.sergzubenko.movieland.web.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.web.mapper.TransformTo;

public class ReviewParamDto{

    @TransformTo(field = "id", clazz = Movie.class)
    @JsonProperty("movieId")
    private Integer movie;

    private String text;

    public Integer getMovie() {
        return movie;
    }

    public void setMovie(Integer movie) {
        this.movie = movie;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}