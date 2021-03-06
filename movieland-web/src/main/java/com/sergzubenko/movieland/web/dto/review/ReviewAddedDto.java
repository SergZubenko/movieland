package com.sergzubenko.movieland.web.dto.review;

import com.sergzubenko.movieland.web.dto.UserIdNameViewDto;

public class ReviewAddedDto {
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
}

