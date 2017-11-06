package com.sergzubenko.movieland.web.dto;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Genre;

import java.util.List;

public class MovieRandomViewDto {
    private Integer id;
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private String description;
    private String picturePath;

    private Double rating;
    private Double price;

    private List<Country> countries;
    private List<Genre> genres;

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

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
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

    public List<Country> getCountries() {
        return countries;
    }

    void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

}
