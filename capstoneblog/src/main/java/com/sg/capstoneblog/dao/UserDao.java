/**
 *@author
 *date:
 *purpose:
 */
package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.User;
import java.util.List;

public interface UserDao {

    User getUserById(int id);
    
    User getUserByEmail(String email);

    List<User> getAllUsers();

    User addUser(User user);

    void editUser(User user);

    void deleteUser(int id);
}
