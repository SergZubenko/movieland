package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Country;
import com.sergzubenko.movieland.persistance.api.CountryDao;
import com.sergzubenko.movieland.persistence.jdbc.mappers.CountryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class JdbcCountryDao implements CountryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sql.country.countiesByIds}")
    private String getCountiesByIdsSQL;

    private CountryMapper mapper = new CountryMapper();

    @Value("${sql.country.allCountries}")
    private String getAllCountiesSQL;

    @Override
    public List<Country> getCountries() {
        return jdbcTemplate.query(getAllCountiesSQL, mapper);
    }

}
