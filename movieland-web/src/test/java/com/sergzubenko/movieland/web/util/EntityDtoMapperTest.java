package com.sergzubenko.movieland.web.util;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.web.dto.MovieCompactViewDto;
import com.sergzubenko.movieland.web.dto.MovieRandomViewDto;
import com.sergzubenko.movieland.web.dto.MovieSingleViewDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EntityDtoMapperTest {

    @Test
    public void map() throws Exception {
        Movie movie = new Movie();
        movie.setId(115);
        movie.setDescription("test desc");
        movie.setYearOfRelease(1900);
        movie.setPrice(12.33);
        movie.setRating(8.09);
        movie.setNameNative("test name native");
        movie.setNameRussian("test russian name");
        movie.setPicturePath("hhtp://index.img");

        List<Country> countries = new ArrayList<>();
        countries.add(new Country(13, "Zimbabve"));
        countries.add(new Country(14, "Uganda"));
        movie.setCountries(countries);

        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(15, "Криминал"));
        genres.add(new Genre(16, "Комедия"));
        movie.setGenres(genres);


        MovieCompactViewDto dto1 = EntityDtoMapper.map(movie, MovieCompactViewDto.class);
        //System.out.println(EntityDtoMapper.map(movie, MovieCompactViewDto.class));

        assertEquals(movie.getId(), dto1.getId());
        assertEquals(movie.getYearOfRelease(), dto1.getYearOfRelease());
        assertEquals(movie.getPrice(), dto1.getPrice());
        assertEquals(movie.getNameNative(), dto1.getNameNative());
        assertEquals(movie.getNameRussian(), dto1.getNameRussian());

        //System.out.println(EntityDtoMapper.map(movie, MovieRandomViewDto.class));
        MovieRandomViewDto dto2 = EntityDtoMapper.map(movie, MovieRandomViewDto.class);
        assertEquals(movie.getId(), dto2.getId());
        assertEquals(movie.getYearOfRelease(), dto2.getYearOfRelease());
        assertEquals(movie.getPrice(), dto2.getPrice());
        assertEquals(movie.getNameNative(), dto2.getNameNative());
        assertEquals(movie.getNameRussian(), dto2.getNameRussian());
        assertEquals(movie.getDescription(), dto2.getDescription());
        if (movie.getCountries() == null) {
            assertNull(dto2.getCountries());
        } else {
            assertArrayEquals(movie.getCountries().toArray(), dto2.getCountries().toArray());
        }

        if (movie.getGenres() == null) {
            assertNull(dto2.getGenres());
        } else {
            assertArrayEquals(movie.getGenres().toArray(), dto2.getGenres().toArray());
        }

        //System.out.println(EntityDtoMapper.map(movie, MovieSingleViewDto.class));

        MovieSingleViewDto dto3 = EntityDtoMapper.map(movie, MovieSingleViewDto.class);
        assertEquals(movie.getId(), dto3.getId());
        assertEquals(movie.getYearOfRelease(), dto3.getYearOfRelease());
        assertEquals(movie.getPrice(), dto3.getPrice());
        assertEquals(movie.getNameNative(), dto3.getNameNative());
        assertEquals(movie.getNameRussian(), dto3.getNameRussian());
        assertEquals(movie.getDescription(), dto3.getDescription());

        if (movie.getCountries() == null) {
            assertNull(dto3.getCountries());
        } else {
            assertArrayEquals(movie.getCountries().toArray(), dto3.getCountries().toArray());
        }

        if (movie.getGenres() == null) {
            assertNull(dto3.getGenres());
        } else {
            assertArrayEquals(movie.getGenres().toArray(), dto3.getGenres().toArray());
        }

        if (movie.getReviews() == null) {
            assertNull(dto3.getReviews());
        } else {
            assertArrayEquals(movie.getReviews().toArray(), dto3.getReviews().toArray());
        }

    }

}