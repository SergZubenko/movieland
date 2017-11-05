package com.sergzubenko.movieland.entity;

public class Review {

    private Integer id;
    private User user;
    private String review;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "entity.Review{" +
                "id=" + id +
                ", user=" + user +
                ", review='" + review + '\'' +
                '}';
    }
}
