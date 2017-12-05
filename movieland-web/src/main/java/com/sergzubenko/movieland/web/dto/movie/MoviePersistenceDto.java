package com.sergzubenko.movieland.web.dto.movie;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.web.mapper.TransformTo;

import java.util.List;

public class MoviePersistenceDto {
    private Integer id;
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private String picturePath;
    private Double rating;
    private Double price;

    @TransformTo(field = "id", clazz = Country.class)
    private List<Integer> countries;

    @TransformTo(field = "id", clazz = Genre.class)
    private List<Integer> genres;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getNameRussian() {
        return nameRussian;
    }

    public void setNameRussian(String nameRussian) {
        this.nameRussian = nameRussian;
    }

    public String getNameNative() {
        return nameNative;
    }

    public void setNameNative(String nameNative) {
        this.nameNative = nameNative;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Integer> getCountries() {
        return countries;
    }

    public void setCountries(List<Integer> countries) {
        this.countries = countries;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "MoviePersistenceDto{" +
                "id=" + id +
                ", nameRussian='" + nameRussian + '\'' +
                ", nameNative='" + nameNative + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", picturePath='" + picturePath + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", countries=" + countries +
                ", genres=" + genres +
                '}';
    }
}
