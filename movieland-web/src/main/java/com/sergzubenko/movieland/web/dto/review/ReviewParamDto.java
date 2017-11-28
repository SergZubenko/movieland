package com.sergzubenko.movieland.web.dto.review;

public class ReviewParamDto{
    private Integer movieId;

    private String review;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }


}