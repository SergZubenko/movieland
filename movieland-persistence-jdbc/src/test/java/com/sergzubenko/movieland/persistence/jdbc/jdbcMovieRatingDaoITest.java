package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.MovieRating;
import com.sergzubenko.movieland.entity.MovieUserVote;
import com.sergzubenko.movieland.persistance.api.MovieRatingDao;
import com.sergzubenko.movieland.persistence.jdbc.config.PersistenceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
public class jdbcMovieRatingDaoITest {

    @Autowired
    private
    MovieRatingDao ratingDao;

   @Test
   @Transactional
    public void persistVotes() throws Exception {
        List<MovieUserVote> votes = new ArrayList<>(2);

        MovieUserVote vote = new MovieUserVote();
        vote.setMovieId(1);
        vote.setRating(9.2);
        vote.setUserId(1);
        votes.add(vote);

        vote = new MovieUserVote();
        vote.setMovieId(1);
        vote.setRating(9.6);
        vote.setUserId(2);
        votes.add(vote);

        ratingDao.persistVotes(votes);

        MovieRating rating = ratingDao.getRating(1).orElseThrow(RuntimeException::new);
        assertEquals(9.4, rating.getRating(), 0.0d);
    }

}