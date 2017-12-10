package com.sergzubenko.movieland.service.impl;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.service.api.CurrencyService;
import com.sergzubenko.movieland.service.impl.cache.NBUCurrencyCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_UP;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private NBUCurrencyCache currencyCache;

    @Override
    public void setMoviePrice(Movie movie, String currency){
        if (currency != null) {
            double rate = getRate(currency);
            if (rate != 0) {
                movie.setPrice((new BigDecimal(movie.getPrice())).divide(new BigDecimal(rate), 2, ROUND_UP).doubleValue());
            }
            else
            {
                movie.setPrice(0d);
            }
        }
    }

    private double getRate(String currency) {
        return currencyCache.getRate(currency);
    }

}
