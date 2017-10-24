package com.sergzubenko.data.dao.jdbc;

import com.sergzubenko.data.dao.MovieDao;
import com.sergzubenko.data.dao.mappers.MovieMapper;
import com.sergzubenko.data.enums.SortOrder;
import com.sergzubenko.data.repository.Movie;
import com.sergzubenko.data.utils.SortedColumnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by sergz on 24.10.2017.
 */
@Repository
public class JdbcTemplateMovieDao implements MovieDao {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Autowired
    MovieMapper movieMapper;

    @Autowired
    SortedColumnMapper sortedColumnMapper;

    
    final String GET_BY_ID = "select id, movie_name, movie_name_origin, year, description, rating, price, picture_path from movies where id = ?";

    final String GET_ALL = "select id, movie_name, movie_name_origin, year, description, rating, price, picture_path from movies";

    final String GET_RANDOM_3 = "select id, movie_name, movie_name_origin, year, description, rating, price, picture_path from  movies order by rand() LIMIT 3";

    final String GET_BY_GENRE = "SELECT m.id, movie_name, movie_name_origin, year, description, rating, price, picture_path FROM  movies m\n" +
            "  JOIN movies_genres  mg ON mg.movie_id = m.id\n" +
            "WHERE mg.genre_id = ?";

    final String ORDER_BY = " order by ";

    @Override
    public Movie getMovieById(Integer id) {
        Movie movie = jdbcTemplate.queryForObject(GET_BY_ID, new Object[]{id},movieMapper);
        return movie;
    }

    @Override
    public List<Movie> getAllMovies() {
        return jdbcTemplate.query(GET_ALL,  movieMapper);
    }

    @Override
    public List<Movie> getRandomMovies() {
        return jdbcTemplate.query(GET_RANDOM_3,  movieMapper);
    }

    @Override
    public List<Movie> getByGenre(Integer genreId) {
        return jdbcTemplate.query(GET_BY_GENRE, new Object[]{genreId},movieMapper);
    }

    @Override
    public List<Movie> getAllMovies(Map<String, String> params) {
        if (params==null || params.size() == 0){
            return getAllMovies();
        }
        StringBuilder stringBuilder = new StringBuilder(GET_ALL);
        stringBuilder.append(ORDER_BY);

        char delimiter = ' ';
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try{
                String columnName = sortedColumnMapper.getColumnName(Movie.class, entry.getKey());
                String ascDesc = SortOrder.getByName(entry.getValue()).name();
                stringBuilder.append(delimiter);
                delimiter = ',';
                stringBuilder.append("\n");
                stringBuilder.append(columnName);
                stringBuilder.append(" ");
                stringBuilder.append(ascDesc);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(stringBuilder.toString());
        return jdbcTemplate.query(stringBuilder.toString(),  movieMapper);
    }
}
