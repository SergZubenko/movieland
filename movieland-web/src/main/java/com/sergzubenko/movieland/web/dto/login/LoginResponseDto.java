package com.sergzubenko.movieland.web.dto.login;

public class LoginResponseDto {
    private String uuid;

    private String nickname;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {

        this.uuid = uuid;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
