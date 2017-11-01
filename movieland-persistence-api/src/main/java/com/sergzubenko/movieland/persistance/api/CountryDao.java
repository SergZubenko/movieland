package com.sergzubenko.movieland.persistance.api;

import com.sergzubenko.movieland.entity.Country;

import java.util.List;
import java.util.Set;

public interface CountryDao {
    List<Country> getCountries();
}
