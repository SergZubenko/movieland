package com.sergzubenko;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by sergz on 23.10.2017.
 */
public class DatabaseLoader {
    private static  final String CONNECTIONSTRING = "jdbc:mysql://localhost:3306/movieland";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static int CONNECTION_TIMEOUT = 5;
    private static Connection connection;

    private static Connection getConnection() {
        try {
            if (connection == null || !connection.isValid(CONNECTION_TIMEOUT)) {
                Class.forName("com.mysql.jdbc.Driver");
                Properties properties = new Properties();
                properties.put("user", USERNAME);
                properties.put("password", PASSWORD);
                properties.put("rewriteBatchedStatements", "True");

                connection = DriverManager.getConnection(CONNECTIONSTRING, properties);
                connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't connect to the database", e);
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
            throw new RuntimeException("Driver is not found", ce);
        }
        return connection;
    }

    @BeforeClass
    public static void prepareBase() {
        connection = getConnection();
    }

//    @Test
    public void loadData() throws IOException, SQLException {
        loadGenres();
        loadUsers();
        loadMovies();
        loadPosters();
        loadReviews();
    }

    public void loadGenres() throws IOException, SQLException {
        final String CLEAR = "TRUNCATE TABLE genres";
        final String INSERT = "INSERT INTO genres(genre) VALUES (?)";

        Path path = Paths.get("./resources/inputdata/genre.txt");

        //truncate table genres
        connection.prepareStatement(CLEAR).execute();

        //execute insert
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            Files.lines(path)
                    .filter(str -> !"".equals(str))
                    .map(value->value.replace("\uFEFF", ""))
                    .forEach((name) -> {
                        try {
                            preparedStatement.setString(1, name);
                            preparedStatement.addBatch();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
            preparedStatement.executeBatch();
        }
        connection.commit();
    }


    public void loadUsers() throws IOException, SQLException {
        final String CLEAR = "TRUNCATE TABLE users";
        final String INSERT = "INSERT INTO users(username, email, password) VALUES (?,?,?)";
        Path path = Paths.get("./resources/inputdata/user.txt");
        //truncate table genres
        connection.prepareStatement(CLEAR).execute();
        class Index {
            int ind = 1;
        }
        final Index index = new Index();
        //execute insert
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            Files.lines(path)
                    .filter(str -> !"".equals(str))
                    .map(value->value.replace("\uFEFF", ""))
                    .forEach((value) -> {
                        try {
                            preparedStatement.setString(index.ind, value);
                            if (index.ind == 3) {
                                preparedStatement.addBatch();
                                index.ind = 1;
                            } else {
                                index.ind++;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
            preparedStatement.executeBatch();
            connection.commit();
        }
    }


    public void loadMovies() throws IOException, SQLException {
        final String CLEAR = "TRUNCATE TABLE movies";
        final String CLEAR_COUNTRIES = "TRUNCATE TABLE countries";
        //final String CLEAR_GENRES = "TRUNCATE TABLE genres";
        final String CLEAR_MOVIE_GENRES = "TRUNCATE TABLE movies_countries";
        final String CLEAR_MOVIE_COUNTRIES = "TRUNCATE TABLE movies_genres";

        final String INSERT =
                "INSERT INTO movies(movie_name, movie_name_origin, year, description, rating, price) " +
                        "VALUES (?,?,?,?,?,?)";
        final String INSERT_GENRES = "INSERT INTO movies_genres(movie_id, genre_id) VALUES(?,?)";
        final String INSERT_COUNTRIES = "INSERT INTO movies_countries(movie_id, country_id) VALUES(?,?)";

        Path path = Paths.get("./resources/inputdata/movie.txt");
        //truncate table genres
        connection.prepareStatement(CLEAR).execute();
        connection.prepareStatement(CLEAR_COUNTRIES).execute();
        connection.prepareStatement(CLEAR_MOVIE_GENRES).execute();
        connection.prepareStatement(CLEAR_MOVIE_COUNTRIES).execute();


        class Index {
            int ind = 1;
        }

        List<List<Integer>> genres = new ArrayList<>();
        List<List<Integer>> countries = new ArrayList<>();
        int[] updatedRowCounts;

        final Index index = new Index();
        //execute insert
        ResultSet rs;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            Files.lines(path)
                    .filter(str -> !"".equals(str))
                    .map(value->value.replace("\uFEFF", ""))
                    .forEach((value) -> {

//
//                        Начало/Inception
//                        2010
//                        США, Великобритания
//                        фантастика, боевик, триллер, драма, детектив
//                        Кобб — талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим. Редкие способности Кобба сделали его ценным игроком в привычном к предательству мире промышленного шпионажа, но они же превратили его в извечного беглеца и лишили всего, что он когда-либо любил.
//                        rating:8.6
//                        price:130.00

                        try {
                            if (index.ind == 1) {
                                String[] names = value.split("/");
                                preparedStatement.setString(1, names[0]);
                                preparedStatement.setString(2, names[1]);
                            } else if (index.ind == 2) {
                                preparedStatement.setInt(3, Integer.parseInt(value));
                            } else if (index.ind == 3) {
                                countries.add(getCounties(value));
                            } else if (index.ind == 4) {
                                genres.add(getGenres(value));
                            } else if (index.ind == 5) {
                                preparedStatement.setString(4, value);
                            } else if (index.ind == 6) {
                                preparedStatement.setDouble(5, Double.parseDouble(value.replace("rating:", "")));
                            } else if (index.ind == 7) {
                                preparedStatement.setDouble(6, Double.parseDouble(value.replace("price:", "")));
                            }

                            if (index.ind == 7) {
                                preparedStatement.addBatch();
                                index.ind = 1;
                            } else {
                                index.ind++;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });

            updatedRowCounts = preparedStatement.executeBatch();
            rs = preparedStatement.getGeneratedKeys();

            //update genres and countries
            PreparedStatement psGenres = connection.prepareStatement(INSERT_GENRES);
            PreparedStatement psCountries = connection.prepareStatement(INSERT_COUNTRIES);
            for (int i = 0; i < updatedRowCounts.length; i++) {
                if (updatedRowCounts[i] > 0 && rs.next()) {
                    Integer movieId = rs.getInt(1);
                    for (Integer id : genres.get(i)) {
                        psGenres.setInt(1, movieId);
                        psGenres.setInt(2, id);
                        psGenres.addBatch();
                    }
                    for (Integer id : countries.get(i)) {
                        psCountries.setInt(1, movieId);
                        psCountries.setInt(2, id);
                        psCountries.addBatch();
                    }
                }
            }
            psGenres.executeBatch();
            psGenres.close();
            psCountries.executeBatch();
            psCountries.close();
            preparedStatement.close();
        }

        connection.commit();
    }

    private List<Integer> getGenres(String genreStr) {
        List<Integer> genres = new ArrayList<>();
        for (String value : genreStr.split(", ")) {
            genres.add(getCreateEntityByColumnValue("genres", "genre", value, true));
        }
        return genres;
    }

    private List<Integer> getCounties(String valuesStr) {
        List<Integer> values = new ArrayList<>();
        for (String value : valuesStr.split(", ")) {
            values.add(getCreateEntityByColumnValue("countries", "country", value, true));
        }
        return values;
    }


    public void loadReviews() throws SQLException, IOException {
        final String CLEAR = "TRUNCATE TABLE reviews";
        final String INSERT = "INSERT INTO reviews(movie_id, user_id, review) VALUES (?,?,?)";
        Path path = Paths.get("./resources/inputdata/review.txt");
        //truncate table genres
        connection.prepareStatement(CLEAR).execute();
        class Index {
            int ind = 1;
        }
        final Index index = new Index();
        //execute insert
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            Files.lines(path, Charset.forName("UTF-8"))
                    .filter(str -> !"".equals(str))
                    .map(value->value.replace("\uFEFF", ""))
                    .forEach((value) -> {
                        try {
                            if (index.ind == 1) {
                                preparedStatement.setInt(1, getCreateEntityByColumnValue("movies", "movie_name", value, false));
                            } else if (index.ind == 2) {
                                preparedStatement.setInt(2, getCreateEntityByColumnValue("users", "username", value, false));
                            } else if (index.ind == 3) {
                                preparedStatement.setString(3, value);
                            }
                            ;

                            if (index.ind == 3) {
                                preparedStatement.addBatch();
                                index.ind = 1;
                            } else {
                                index.ind++;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
            preparedStatement.executeBatch();
            connection.commit();
        }
    }

    public void loadPosters() throws IOException, SQLException {
        final String INSERT = "INSERT INTO posters(movie_id, url) VALUES (?,?)";
        final String CLEAR = "truncate table posters";
        Path path = Paths.get("./resources/inputdata/poster.txt");

        connection.prepareStatement(CLEAR).execute();

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            Files.lines(path)
                    .filter(value -> !"".equals(value))
                    .map(value->value.replace("\uFEFF", ""))
                    .forEach(value -> {
                        //Звёздные войны: Эпизод 4 – Новая надежда https://images-na.ssl-images-amazon.com/images/M/MV5BYTUwNTdiMzMtNThmNS00ODUzLThlMDMtMTM5Y2JkNWJjOGQ2XkEyXkFqcGdeQXVyNzQ1ODk3MTQ@._V1._SX140_CR0,0,140,209_.jpg
                        int index = value.indexOf("http");
                        try {
                            preparedStatement.setInt(1,getCreateEntityByColumnValue("movies","movie_name", value.substring(0, index - 1), false));
                            preparedStatement.setString(2, value.substring(index));
                            preparedStatement.execute();
                            connection.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
        }

    }


    private int getCreateEntityByColumnValue(String table, String column, String name, boolean create) {
        final String SQL = "select id from %entity% where %column% = ?";
        final String SQLInsert = "insert into %entity%(%column%) values (?)";

        String preparedSQL = SQL.replace("%entity%", table);
        preparedSQL = preparedSQL.replace("%column%", column);

        try (PreparedStatement preparedStatement = connection.prepareStatement(preparedSQL)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!create) {
            throw new RuntimeException("Something going wrong with " + name);
        }

        preparedSQL = SQLInsert.replace("%entity%", table);
        preparedSQL = preparedSQL.replace("%column%", column);

        try (PreparedStatement preparedStatement = connection.prepareStatement(preparedSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.execute();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Something going wrong with " + name);
    }

}
