package com.sergzubenko.data.repository;

/**
 * Created by sergz on 23.10.2017.
 */
public class Review {

    Integer id;
    User user;
    String review;

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
        return "Review{" +
                "id=" + id +
                ", user=" + user +
                ", review='" + review + '\'' +
                '}';
    }
}
