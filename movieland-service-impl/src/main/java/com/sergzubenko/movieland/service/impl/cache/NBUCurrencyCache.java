package com.sergzubenko.movieland.service.impl.cache;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class NBUCurrencyCache {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<String> currencies;

    volatile private Map<String, Double> ratesCache = new HashMap<>();

    @Value("${currency.supportedCurrencies}")
    private String supportedCurrencies;

    @Value("${currency.nbuUrl}")
    private String url;

    @Value("${currency.baseCurrency}")
    private String baseCurrency;

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
            log.info("Updating  NBU rates");
            loadFromServer();
        } catch (IOException e) {
            log.warn("Update rates failed. ", e);
        }
    }

    private void loadFromServer() throws IOException {

        List<CurrencyJSONFormat> loadedCurrencies = objectMapper.readValue(new URL(url),
                new TypeReference<List<CurrencyJSONFormat>>() {
                });

        ratesCache = loadedCurrencies.stream()
                .filter(rate -> currencies.contains(rate.currency))
                .collect(Collectors.toMap(rate -> rate.currency.toUpperCase(), rate -> rate.rate));
    }


    //RECEIVED RATE JSON FORMAT
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CurrencyJSONFormat {
        private double rate;

        @JsonProperty("cc")
        private String currency;
    }
}

