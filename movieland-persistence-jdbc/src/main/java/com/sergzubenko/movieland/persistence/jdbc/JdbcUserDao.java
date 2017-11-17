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
    private final UserMapper userMapper = new UserMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sql.user.getAll}")
    String getAllUsersSql;


    @Value("${sql.user.getByEmail}")
    String getByEmailSql;

    @Value("${sql.user.getUserRoles}")
    String getUserRolesSql;

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(getAllUsersSql, userMapper);
    }

    @Override
    public User getByEmail(String email) {
        return jdbcTemplate.queryForObject(getByEmailSql, new Object[]{email}, userMapper);
    }

    @Override
    public Set<UserRole> getRoles(User user) {

        HashSet<UserRole> roles = new HashSet<>();
        jdbcTemplate.query(getUserRolesSql,new Object[]{user.getId()},
                rs -> {
            roles.add(UserRole.getByName(rs.getString("ROLE")));
        });
        return roles;
    }

}
