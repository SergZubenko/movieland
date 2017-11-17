package com.sergzubenko.movieland.service.impl.security.token;

import com.sergzubenko.movieland.service.api.security.AccessToken;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenCache {

    private final ConcurrentHashMap<String, AccessToken> tokenCache;

    public TokenCache() {
        tokenCache = new ConcurrentHashMap<>();
    }

    public AccessToken getToken(String tokenId) {
        return tokenCache.get(tokenId);
    }

    public void registerToken(AccessToken token) {
        tokenCache.put(token.uid(), token);
    }

    @Scheduled(fixedDelayString = "${token.cacheCleanupTimeout}")
    private void cleanup() {
        tokenCache.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

}
