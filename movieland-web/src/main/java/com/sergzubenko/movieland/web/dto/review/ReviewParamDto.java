package com.sergzubenko.movieland.web.dto.review;

public class ReviewParamDto{
    private Integer movieId;

    private String text;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}