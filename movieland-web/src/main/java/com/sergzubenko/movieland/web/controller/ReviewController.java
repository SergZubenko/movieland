package com.sergzubenko.movieland.web.controller;

import com.sergzubenko.movieland.entity.Review;
import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.service.api.ReviewService;
import com.sergzubenko.movieland.service.api.security.UserPrincipal;
import com.sergzubenko.movieland.web.dto.review.ReviewAddedDto;
import com.sergzubenko.movieland.web.dto.review.ReviewParamDto;
import com.sergzubenko.movieland.web.mapper.ObjectTransformer;
import com.sergzubenko.movieland.web.security.annotation.HasRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sergzubenko.movieland.web.mapper.ObjectTransformer.transform;


@RestController
@CrossOrigin
public class ReviewController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReviewService reviewService;

    @RequestMapping(value = "/review", method = RequestMethod.POST)
    @HasRole(UserRole.USER)
    public ResponseEntity<?> postReview(@RequestBody ReviewParamDto reviewParamDto, UserPrincipal principal) {
        User user = principal.getUser();
        logger.info("User {} is trying to add/update review ", user.getEmail());

        Review review = prepareReviewToSave(reviewParamDto, user);
        reviewService.persist(review);

        logger.info("User {} added review ", user.getEmail());
        return new ResponseEntity<>(transform(review, ReviewAddedDto.class), HttpStatus.OK);
    }

    private Review prepareReviewToSave(ReviewParamDto reviewParamDto, User user){
        Review review  = ObjectTransformer.transform(reviewParamDto, Review.class);
        review.setUser(user);
        return review;
    }

}
