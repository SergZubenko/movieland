package com.sergzubenko.movieland.persistence.jdbc.mapper;

import com.sergzubenko.movieland.entity.Review;
import com.sergzubenko.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserReviewMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        Review review = new Review();
        review.setId(rs.getInt("review_id"));
        review.setText(rs.getString("text"));

        User user = new User();
        user.setNickname(rs.getString("username"));
        user.setId(rs.getInt("user_id"));
        review.setUser(user);
        return review;
    }
}
