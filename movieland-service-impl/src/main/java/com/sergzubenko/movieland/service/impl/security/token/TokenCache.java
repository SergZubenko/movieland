package com.sergzubenko.movieland.service.impl.security.token;

import com.sergzubenko.movieland.service.api.security.AccessToken;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenCache {

    private final ConcurrentHashMap<String, AccessToken> tokenCache;

    public TokenCache() {
        tokenCache = new ConcurrentHashMap<>();
    }

    public Optional<AccessToken> getToken(String tokenId) {
        return Optional.ofNullable(tokenCache.get(tokenId));
    }

    public void registerToken(AccessToken token) {
        tokenCache.put(token.uid(), token);
    }

    public void removeToken(String uid) {
        tokenCache.remove(uid);
    }

    @Scheduled(fixedDelayString = "${token.cacheCleanupTimeout}")
    private void cleanup() {
        tokenCache.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

}
