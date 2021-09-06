package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Role;
import com.sg.capstoneblog.dto.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author pauldelaney
 */
@SpringBootTest
public class UserDaoDBTest {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    public UserDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            userDao.deleteUser(user.getUserId());
        }

        List<Role> roles = roleDao.getAllRoles();
        for (Role role : roles) {
            roleDao.deleteRoleById(role.getRoleId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAndAddUser() {

        Role role = new Role();
        role.setName("TEST ROLE");
        role = roleDao.addRole(role);

        // Create User
        User user = new User();
        user.setRole(role);
        user.setFirstName("Test first name");
        user.setLastName("Test Last name");
        user.setEmail("Test email");
        user.setPassword("Test password");

        user = userDao.addUser(user);

        User fromDao = userDao.getUserById(user.getUserId());

        assertEquals(user, fromDao);
    }

    @Test
    public void testGetAllUsers() {
        Role role = new Role();
        role.setName("Test name");
        role = roleDao.addRole(role);
        // Create User
        User user = new User();
        user.setRole(role);
        user.setFirstName("Test first name");
        user.setLastName("Test Last name");
        user.setEmail("Test email");
        user.setPassword("Test password");

        user = userDao.addUser(user);

        // Create second user
        User user2 = new User();
        user2.setRole(role);
        user2.setFirstName("Test first name 2");
        user2.setLastName("Test Last name 2");
        user2.setEmail("Test email 2");
        user2.setPassword("Test password 2");

        user2 = userDao.addUser(user2);

        List<User> users = userDao.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(user));
        assertTrue(users.contains(user2));

    }

    @Test
    public void testEditUser() {

        Role role = new Role();
        role.setName("Test name");
        role = roleDao.addRole(role);
        // Create User
        User user = new User();
        user.setRole(role);
        user.setFirstName("Test first name");
        user.setLastName("Test Last name");
        user.setEmail("Test email");
        user.setPassword("Test password");

        user = userDao.addUser(user);
        User fromDao = userDao.getUserById(user.getUserId());

        assertEquals(user, fromDao);

        user.setFirstName("New First Name Test");
        userDao.editUser(user);

        assertNotEquals(user, fromDao);

        fromDao = userDao.getUserById(user.getUserId());
        assertEquals(user, fromDao);
    }

    @Test
    public void testDeleteUser() {
        Role role = new Role();
        role.setName("Test name");
        role = roleDao.addRole(role);
        // Create User
        User user = new User();
        user.setRole(role);
        user.setFirstName("Test first name");
        user.setLastName("Test Last name");
        user.setEmail("Test email");
        user.setPassword("Test password");

        user = userDao.addUser(user);
        User fromDao = userDao.getUserById(user.getUserId());

        assertEquals(user, fromDao);

        userDao.deleteUser(user.getUserId());

        fromDao = userDao.getUserById(user.getUserId());
        assertNull(fromDao);
    }
}
