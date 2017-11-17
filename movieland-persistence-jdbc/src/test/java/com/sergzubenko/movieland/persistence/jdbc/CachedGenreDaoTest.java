package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.persistence.jdbc.config.PersistenceConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
public class CachedGenreDaoTest {

    @Autowired
    private CachedGenreDao cachedGenreDao;

    @Test
    @Ignore
    public void setCachedGenreDaoTest() throws InterruptedException {
        long tms;

        for (int i = 0; i < 50; i++) {
            tms = System.nanoTime();
            cachedGenreDao.getAll();
            System.out.println(System.nanoTime() - tms);
            Thread.sleep(100);
        }

    }
}
