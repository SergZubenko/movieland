package com.sergzubenko.movieland.web.dto;

public class MovieCompactDto {

    private Integer id;
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private String picturePath;
    private Double rating;
    private Double price;

    public Integer getId() {
        return id;
    }

    void setId(Integer id) {
        this.id = id;
    }

    public String getPicturePath() {
        return picturePath;
    }

    void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getNameRussian() {
        return nameRussian;
    }

    void setNameRussian(String nameRussian) {
        this.nameRussian = nameRussian;
    }

    public String getNameNative() {
        return nameNative;
    }

    void setNameNative(String nameNative) {
        this.nameNative = nameNative;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Double getRating() {
        return rating;
    }

    void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    void setPrice(Double price) {
        this.price = price;
    }

}

