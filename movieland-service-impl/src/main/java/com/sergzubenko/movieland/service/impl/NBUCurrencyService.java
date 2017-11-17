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

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NBUCurrencyService implements CurrencyService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, Double> ratesCache;

    @Value("${currency.supportedCurrencies}")
    private String supportedCurrencies;

    @Value("${currency.nbuUrl}")
    private String url;

    @Value("${currency.baseCurrency}")
    private String baseCurrency;

    private void updateRates0() throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        List<Map<String, String>> loadedCurrencies;

        //To avoid DH SSL error
        Security.insertProviderAt(new BouncyCastleProvider(), 1);

        if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
            System.out.println("Cant retrieve currencies. Cache was not updated");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        loadedCurrencies = objectMapper.readValue(new InputStreamReader(con.getInputStream()), new TypeReference<List<Map<String, String>>>() {
        });

        List<String> currencies = Arrays.asList(supportedCurrencies.split(","));

        ratesCache = loadedCurrencies.stream()
                .filter(c -> currencies.contains(c.get("cc")))
                .collect(Collectors.toMap(c -> c.get("cc").toUpperCase(), r -> Double.valueOf(r.get("rate"))));
    }

    @Scheduled(fixedDelayString = "${currency.updateInterval}", initialDelay = 0)
    private void updateRates() {
        try {
            logger.info("Updating  NBU rates");
            updateRates0();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Update rates failed. ", e);
        }
    }

    @Override
    public double getRate(String currency) {
        if (baseCurrency.equals(currency)) {
            return 1;
        } else
            return ratesCache.get(currency.toUpperCase());
    }
}

