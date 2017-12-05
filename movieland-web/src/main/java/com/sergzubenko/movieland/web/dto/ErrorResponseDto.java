package com.sergzubenko.movieland.web.dto;

public class ErrorResponseDto {
    private final String url;
    private final  String error;

    public ErrorResponseDto(String url, String error) {
        this.url = url;
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public String getError() {
        return error;
    }
}
