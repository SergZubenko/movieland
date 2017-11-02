package com.sergzubenko.movieland.controllers;

import com.sergzubenko.movieland.config.WebAppConfig;
import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.entity.Genre;
import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.serialize.JSONConverter;
import com.sergzubenko.movieland.service.api.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebAppConfig.class)
@WebAppConfiguration
@DirtiesContext
public class MoviesControllerTest {

    private MockMvc mvc;

    @Mock
    private MovieService movieService;

    @Spy
    JSONConverter jsonConverter = new JSONConverter();

    private String exColumnsAllMovies = "countries,genres,description";

    @InjectMocks
    private MoviesController moviesController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .standaloneSetup(moviesController)
                .build();

        org.springframework.test.util.ReflectionTestUtils.setField(
                moviesController, "exColumnsAllMovies", exColumnsAllMovies);
    }

    private void initMock() {
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();
        movie.setId(115);
        movie.setDescription("test desc");
        movie.setYearOfRelease(1900);
        movie.setPrice(12.33);
        movie.setRating(8.09);
        movie.setNameNative("test name native");
        movie.setNameRussian("test russian name");
        movie.setPicturePath("hhtp://index.img");
        List<Country> countries = movie.getCountries();
        countries.add(new Country(13,"Zimbabve"));
        countries.add(new Country(14,"Uganda"));

        List<Genre> genres = movie.getGenres();
        genres.add(new Genre(15,"Криминал"));
        genres.add(new Genre(16,"Комедия"));

        movies.add(movie);

        when(movieService.getAllMovies(any())).thenReturn(movies);
        when(movieService.getRandomMovies()).thenReturn(movies);
        when(movieService.getByGenre(any())).thenReturn(movies);
    }

    @Test
    public void getAllMovies() throws Exception {
        initMock();
        mvc.perform(
                get("/movie")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(115))
                .andExpect(jsonPath("$[0].description").doesNotExist())
                .andExpect(jsonPath("$[0].yearOfRelease").value(1900))
                .andExpect(jsonPath("$[0].price").value(12.33))
                .andExpect(jsonPath("$[0].rating").value(8.09))
                .andExpect(jsonPath("$[0].nameNative").value("test name native"))
                .andExpect(jsonPath("$[0].nameRussian").value("test russian name"))
                .andExpect(jsonPath("$[0].picturePath").value("hhtp://index.img"))
                .andExpect(jsonPath("$[0].countries").doesNotExist())
                .andExpect(jsonPath("$[0].genres").doesNotExist())
        ;
    }

    @Test
    public void getRandomMovies() throws Exception {
        initMock();
        mvc.perform(
                get("/movie/random")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[0].id").value(115))
                .andExpect(jsonPath("$[0].description").value("test desc"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(1900))
                .andExpect(jsonPath("$[0].price").value(12.33))
                .andExpect(jsonPath("$[0].rating").value(8.09))
                .andExpect(jsonPath("$[0].nameNative").value("test name native"))
                .andExpect(jsonPath("$[0].nameRussian").value("test russian name"))
                .andExpect(jsonPath("$[0].picturePath").value("hhtp://index.img"))
                .andExpect(jsonPath("$[0].countries[0].name").value("Zimbabve"))
                .andExpect(jsonPath("$[0].genres[1].name").value("Комедия"));
    }


    @Test
    public void getByGenre() throws Exception {
        initMock();
        mvc.perform(
                get("/movie/genre/1")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[0].id").value(115))
                .andExpect(jsonPath("$[0].description").doesNotExist())
                .andExpect(jsonPath("$[0].yearOfRelease").value(1900))
                .andExpect(jsonPath("$[0].price").value(12.33))
                .andExpect(jsonPath("$[0].rating").value(8.09))
                .andExpect(jsonPath("$[0].nameNative").value("test name native"))
                .andExpect(jsonPath("$[0].nameRussian").value("test russian name"))
                .andExpect(jsonPath("$[0].picturePath").value("hhtp://index.img"))
                .andExpect(jsonPath("$[0].countries").doesNotExist())
                .andExpect(jsonPath("$[0].genres").doesNotExist());
    }

}