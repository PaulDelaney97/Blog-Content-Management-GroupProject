/**
 *@author RossMitchell + Paul Delaney
 *email: ross.mitchell1999@gmail.com + paul.delaney120197@gmail.com
 *date: 25/08/2021
 *purpose:
 */
package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dao.RoleDaoDB.RoleMapper;
import com.sg.capstoneblog.dto.Role;
import com.sg.capstoneblog.dto.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoDB implements UserDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public User getUserById(int id) {
        try {
            final String GET_USER_BY_ID = "SELECT * FROM user WHERE userId = ?";
            User user = jdbc.queryForObject(GET_USER_BY_ID, new UserMapper(), id);
            user.setRole(getRoleForUser(id));
            return user;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    //------------- Method to get Role object for User -----------------------
    private Role getRoleForUser(int id) {
        final String SELECT_ROLE_FOR_USER = "SELECT r.* FROM role r "
                + "JOIN user u ON u.roleId = r.roleId WHERE u.userId = ?";
        return jdbc.queryForObject(SELECT_ROLE_FOR_USER, new RoleMapper(), id);
    }
    //------------------------------------------------------------------------

    @Override
    public List<User> getAllUsers() {
        final String GET_ALL_USERS = "SELECT * FROM user";
        List<User> users = jdbc.query(GET_ALL_USERS, new UserMapper());
        associateUserAndRoles(users);
        return users;
    }

    //------------- Method to associate users and roles-----------------------
    private void associateUserAndRoles(List<User> users) {
        for (User user : users) {
            user.setRole(getRoleForUser(user.getUserId()));
        }
    }
    
    @Override
    public User getUserByEmail(String email) {
        try {
            final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
            User user = jdbc.queryForObject(GET_USER_BY_EMAIL, new UserMapper(), email);
            user.setRole(getRoleForUser(user.getUserId()));
            return user;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public User addUser(User user) {
        final String INSERT_USER = "INSERT INTO user(roleId, firstName, lastName, email, password) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_USER,
                user.getRole().getRoleId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        user.setUserId(newId);
        return user;
    }

    @Override
    public void editUser(User user) {
        final String UPDATE_USER = "UPDATE user SET roleId = ?, firstName = ?, lastName = ?, email = ?, password = ? WHERE userId = ?";
        jdbc.update(UPDATE_USER,
                user.getRole().getRoleId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getUserId());
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        final String DELETE_POST = "DELETE FROM post WHERE userId = ?";
        jdbc.update(DELETE_POST, id);

        final String DELETE_PAGE = "DELETE FROM staticPage WHERE userId = ?";
        jdbc.update(DELETE_PAGE, id);

        final String DELETE_USER = "DELETE FROM user WHERE userId = ?";
        jdbc.update(DELETE_USER, id);
    }

    public static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int index) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("userId"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));

            return user;
        }
    }
}
