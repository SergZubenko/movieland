package com.sergzubenko.movieland.persistence.jdbc.mappers;

import com.sergzubenko.movieland.entity.Genre;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

public class GenreMapperTest {
    @Test
    public void mapRow() throws Exception {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getString(anyString())).thenReturn("test genre");
        Mockito.when(resultSet.getInt(anyString())).thenReturn(10);
        GenreMapper genreMapper = new GenreMapper();
        Genre genre = genreMapper.mapRow(resultSet, 1);
        assertEquals(genre.getId().intValue(), 10);
        assertEquals(genre.getName(), "test genre");
    }

}