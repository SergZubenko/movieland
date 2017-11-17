package com.sergzubenko.movieland.service.impl.security.token;

import com.sergzubenko.movieland.service.api.security.AccessToken;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UUIDBasedTokenTest {
    @Test
    public void isExpired() throws Exception {
        AccessToken token = new UUIDBasedToken(null, LocalDateTime.now().minusSeconds(1));
        assertTrue(token.isExpired());
    }

    @Test
    public void invalidate() throws Exception {
        AccessToken token = new UUIDBasedToken(null, LocalDateTime.now().plusMinutes(1));
        token.invalidate();
        Thread.sleep(1);
        assertTrue(token.isExpired());
    }

}