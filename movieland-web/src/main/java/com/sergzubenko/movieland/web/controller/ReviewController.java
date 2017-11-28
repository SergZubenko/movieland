package com.sergzubenko.movieland.web.controller;

import com.sergzubenko.movieland.entity.Movie;
import com.sergzubenko.movieland.entity.Review;
import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.api.ReviewService;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;
import com.sergzubenko.movieland.web.dto.review.ReviewAddedDto;
import com.sergzubenko.movieland.web.dto.review.ReviewParamDto;
import com.sergzubenko.movieland.web.security.annotation.HasRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.sergzubenko.movieland.web.mapper.EntityDtoReflectionMapper.map;


@RestController
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger("review controller");

    @Autowired
    private
    ReviewService reviewService;

    @RequestMapping(value = "/review", method = RequestMethod.POST)
    @HasRole(UserRole.USER)
    public ResponseEntity<?> postReview(@RequestBody ReviewParamDto reviewParamDto, Principal principal) {
        User user = ((UserPrincipal) principal).getUser();

        logger.info("User {} is trying to add/update review ", user.getEmail());

        Review review = new Review();
        Movie movie = new Movie();
        movie.setId(reviewParamDto.getMovieId());
        review.setMovie(movie);
        review.setText(reviewParamDto.getReview());
        review.setUser(user);

        reviewService.save(review);
        logger.info("User {} added review ", user.getEmail());
        return new ResponseEntity<>(map(review, ReviewAddedDto.class), HttpStatus.OK);
    }
}
