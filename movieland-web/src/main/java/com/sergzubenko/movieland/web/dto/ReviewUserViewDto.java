package com.sergzubenko.movieland.web.dto;

public class ReviewUserViewDto {

    private Integer id;

    private UserIdNameViewDto user;

    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserIdNameViewDto getUser() {
        return user;
    }

    public void setUser(UserIdNameViewDto user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ReviewUserViewDto{" +
                "id=" + id +
                ", user=" + user +
                ", text='" + text + '\'' +
                '}';
    }
}
