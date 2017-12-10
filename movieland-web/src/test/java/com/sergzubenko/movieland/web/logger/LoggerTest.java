package com.sergzubenko.movieland.web.logger;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {
    @Test
    @Ignore
    public void testLogMail(){
        Logger log = LoggerFactory.getLogger(getClass());
        log.info("trying to send the error mail");
        log.error("some error");
        log.info("mail should be sent");
    }
}
