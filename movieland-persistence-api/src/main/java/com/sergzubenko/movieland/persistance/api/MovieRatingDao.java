package com.sergzubenko.movieland.persistance.api;

import com.sergzubenko.movieland.entity.MovieRating;
import com.sergzubenko.movieland.entity.MovieUserVote;

import java.util.List;
import java.util.Optional;

public interface MovieRatingDao {
    void persistVotes(List<MovieUserVote> votes);

    List<MovieRating> getRating(List<Integer> ids);

    Optional<MovieRating> getRating(Integer id);

}
