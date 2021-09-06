package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Role;
import java.util.List;

/**
 *
 * @author pauldelaney
 */
public interface RoleDao {

    Role getRoleById(int id);

    List<Role> getAllRoles();

    Role addRole(Role role);

    void deleteRoleById(int id);

}
