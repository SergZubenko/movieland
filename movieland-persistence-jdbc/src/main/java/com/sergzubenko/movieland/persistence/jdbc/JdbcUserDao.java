package com.sergzubenko.movieland.persistence.jdbc;

import com.sergzubenko.movieland.entity.User;
import com.sergzubenko.movieland.entity.UserRole;
import com.sergzubenko.movieland.persistance.api.UserDao;
import com.sergzubenko.movieland.persistence.jdbc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class JdbcUserDao implements UserDao {
    private static final UserMapper USER_MAPPER = new UserMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sql.user.getAll}")
    String getAllUsersSql;


    @Value("${sql.user.getByEmail}")
    String getByEmailAndPasswordSql;

    @Value("${sql.user.getUserRoles}")
    String getUserRolesSql;

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(getAllUsersSql, USER_MAPPER);
    }

    @Override
    public User getByEmailAndPassword(String email, String password) {
        return jdbcTemplate.queryForObject(getByEmailAndPasswordSql, USER_MAPPER, email, password);
    }

    @Override
    public Set<UserRole> getRoles(User user) {
        HashSet<UserRole> roles = new HashSet<>();
        jdbcTemplate.query(getUserRolesSql,
                rs -> {
                    UserRole role = UserRole.getByName(rs.getString("ROLE"));
                    roles.add(role);
                },
                user.getId());
        return roles;
    }

}
