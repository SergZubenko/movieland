package com.sergzubenko.movieland.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergzubenko.movieland.service.api.CurrencyService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NBUCurrencyService implements CurrencyService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<String> currencies;

    volatile private Map<String, Double> ratesCache = new HashMap<>();

    @Value("${currency.supportedCurrencies}")
    private String supportedCurrencies;

    @Value("${currency.nbuUrl}")
    private String url;

    @Value("${currency.baseCurrency}")
    private String baseCurrency;

    @Override
    public double getRate(String currency) {
        if (baseCurrency.equalsIgnoreCase(currency)) {
            return 1;
        }
        if (ratesCache.containsKey(currency.toUpperCase())) {
            return ratesCache.get(currency.toUpperCase());
        }
        return 0;
    }

    @PostConstruct
    private void initialize() {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
        currencies = Arrays.asList(supportedCurrencies.split(","));
    }

    @Scheduled(fixedDelayString = "${currency.updateInterval}", initialDelay = 0)
    private void updateRates() {
        try {
            logger.info("Updating  NBU rates");
            loadFromServer();
        } catch (IOException e) {
            logger.warn("Update rates failed. ", e);
        }
    }

    private void loadFromServer() throws IOException {

        List<CurrencyFormat> loadedCurrencies = objectMapper.readValue(new URL(url), new TypeReference<List<CurrencyFormat>>() {
        });

        ratesCache = loadedCurrencies.stream()
                .filter(c -> currencies.contains(c.cc))
                .collect(Collectors.toMap(c -> c.cc.toUpperCase(), r -> r.rate));
    }

    private static class CurrencyFormat {
        private double rate;
        private String cc;
    }
}

