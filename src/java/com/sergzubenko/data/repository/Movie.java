package com.sergzubenko.data.repository;

import java.util.List;

/**
 * Created by sergz on 23.10.2017.
 */
public class Movie {
   Integer id;
   String nameRussian;
   String nameNative;
   Integer yearOfRelease;
   String description;
   String picturePath;
   Double rating;
   Double price;

   List<Country> countries;
   List<Genre> genres;

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
      return "Movie{" +
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
}

