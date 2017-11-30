package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.entity.Review;
import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.persistance.api.MovieDao;
import com.sergzubenko.movieland.persistence.jdbc.config.PersistenceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
@DirtiesContext
public class JdbcReviewUserDaoTest {
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Autowired
    private JdbcReviewUserDao reviewUserDao;

    @Autowired
    private MovieDao movieDao;


    private void checkUser(User user) {
        assertNotNull(user.getNickname());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
    }

    private void checkReview(Review review) {
        assertNotNull(review.getId());
        assertNotNull(review.getUser());
        assertNotNull(review.getText());
        checkUser(review.getUser());
    }

    @Test
    public void enrichMovies() throws Exception {
        List<Movie> movies = movieDao.getRandomMovies();
        reviewUserDao.enrichMovies(movies);
        for (Movie movie : movies) {
            System.out.println(movie);
            List<Review> reviews = movie.getReviews();
            if (reviews != null) {
                for (Review review : reviews) {
                    checkReview(review);
                }
            }
        }
    }

    @Test
    @Rollback
    @Transactional
    public void saveReview() {
        Review review = null;
        for (int i = 0; i <= 5; i++) {
            List<Movie> movies = movieDao.getRandomMovies();
            reviewUserDao.enrichMovies(movies);
            for (Movie movie : movies) {
                if (movie.getReviews() != null) {
                    review = movie.getReviews().get(0);
                    logger.info("found review {}", review);
                    break;
                }
            }
        }

        if (review == null) {
            logger.error("Template review does not found");
            throw new RuntimeException("Template review does not found");
        }

        review.setId(null);
        review.setText("test review");

        reviewUserDao.save(review);
        assertNotNull(review.getId());
        Integer oldId = review.getId();

        reviewUserDao.save(review);

        assertEquals(oldId, review.getId());
    }

}