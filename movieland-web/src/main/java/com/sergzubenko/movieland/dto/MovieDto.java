package com.sergzubenko.movieland.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Genre;

import java.util.ArrayList;
import java.util.List;

@JsonFilter("allMoviesFilter")
public class MovieDto {

    private Integer id;
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private String description;
    private String picturePath;

    private Double rating;
    private Double price;


    private List<Country> countries = new ArrayList<>();

    private List<Genre> genres = new ArrayList<>();


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "entitity.Movie{" +
                "id=" + id +
                ", nameRussian='" + nameRussian + '\'' +
                ", nameNative='" + nameNative + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", description='" + description + '\'' +
                ", picturePath='" + picturePath + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", countries=" + countries +
                ", genres=" + genres +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieDto movie = (MovieDto) o;

        if (getId() != null ? !getId().equals(movie.getId()) : movie.getId() != null) return false;
        if (getNameRussian() != null ? !getNameRussian().equals(movie.getNameRussian()) : movie.getNameRussian() != null)
            return false;
        if (getNameNative() != null ? !getNameNative().equals(movie.getNameNative()) : movie.getNameNative() != null)
            return false;
        if (getYearOfRelease() != null ? !getYearOfRelease().equals(movie.getYearOfRelease()) : movie.getYearOfRelease() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(movie.getDescription()) : movie.getDescription() != null)
            return false;
        if (getPicturePath() != null ? !getPicturePath().equals(movie.getPicturePath()) : movie.getPicturePath() != null)
            return false;
        if (getRating() != null ? !getRating().equals(movie.getRating()) : movie.getRating() != null) return false;
        return getPrice() != null ? getPrice().equals(movie.getPrice()) : movie.getPrice() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getNameRussian() != null ? getNameRussian().hashCode() : 0);
        result = 31 * result + (getNameNative() != null ? getNameNative().hashCode() : 0);
        result = 31 * result + (getYearOfRelease() != null ? getYearOfRelease().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getPicturePath() != null ? getPicturePath().hashCode() : 0);
        result = 31 * result + (getRating() != null ? getRating().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        return result;
    }
}

