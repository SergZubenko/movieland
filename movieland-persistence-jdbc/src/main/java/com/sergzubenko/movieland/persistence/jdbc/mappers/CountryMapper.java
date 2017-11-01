package com.sergzubenko.movieland.persistence.jdbc.mappers;

import com.sergzubenko.movieland.entity.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Country(resultSet.getInt("id"), resultSet.getString("country"));
    }
}
