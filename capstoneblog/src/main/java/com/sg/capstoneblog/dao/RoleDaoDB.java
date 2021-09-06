package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author pauldelaney
 */
@Repository
public class RoleDaoDB implements RoleDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Role getRoleById(int id) {
        try {
            final String SELECT_ROLE_BY_ID = "SELECT * FROM role WHERE roleId = ?";
            Role role = jdbc.queryForObject(SELECT_ROLE_BY_ID, new RoleMapper(), id);
            return role;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Role> getAllRoles() {
        final String GET_ALL_ROLES = "SELECT * FROM role";
        return jdbc.query(GET_ALL_ROLES, new RoleMapper());
    }

    @Override
    @Transactional
    public Role addRole(Role role) {
        final String INSERT_ROLE = "INSERT INTO role(roleName) VALUES(?)";
        jdbc.update(INSERT_ROLE,
                role.getName());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        role.setRoleId(newId);
        return role;
    }

    @Override
    @Transactional
    public void deleteRoleById(int id) {
        final String DELETE_USER = "DELETE FROM user WHERE roleId = ?";
        jdbc.update(DELETE_USER, id);
        final String DELETE_ROLE = "DELETE FROM role WHERE roleId = ?";
        jdbc.update(DELETE_ROLE, id);
    }

    public static final class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int index) throws SQLException {
            Role role = new Role();
            role.setRoleId(rs.getInt("roleId"));
            role.setName(rs.getString("roleName"));

            return role;
        }
    }
}
