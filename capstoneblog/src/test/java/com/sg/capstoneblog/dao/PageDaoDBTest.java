package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Page;
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
public class PageDaoDBTest {

    @Autowired
    PageDao pageDao;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    public PageDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        List<Page> pages = pageDao.getAllPages();
        for (Page page : pages) {
            pageDao.deletePage(page.getStaticPageId());
        }

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
    public void testGetAndAddPage() {

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

        // Create Page + Add to DB
        Page page = new Page();
        page.setUser(user);
        page.setTitle("Test page title");
        page.setContent("Test Content");

        page = pageDao.addPage(page);

        // Get Page from DB
        Page fromDao = pageDao.getPageById(page.getStaticPageId());

        // Assert
        assertEquals(page, fromDao);
    }

    @Test
    public void testGetAllPages() {

        // Create first Role + add to DB
        Role role = new Role();
        role.setName("Test Role");
        role = roleDao.addRole(role);

        // Create first User + add to DB
        User user = new User();
        user.setRole(role);
        user.setFirstName("Test first name");
        user.setLastName("Test Last name");
        user.setEmail("Test email");
        user.setPassword("Test password");
        user = userDao.addUser(user);

        // Create first Page + Add to DB
        Page page = new Page();
        page.setUser(user);
        page.setTitle("Test page title");
        page.setContent("Test Content");

        page = pageDao.addPage(page);

        // Create Second Role + add to DB
        Role role2 = new Role();
        role2.setName("Test Role 2");
        role2 = roleDao.addRole(role);

        // Create second User + add to DB
        User user2 = new User();
        user2.setRole(role2);
        user2.setFirstName("Test first name 2");
        user2.setLastName("Test Last name 2");
        user2.setEmail("Test email 2");
        user2.setPassword("Test password 2");
        user2 = userDao.addUser(user);

        // Create second Page + Add to DB
        Page page2 = new Page();
        page2.setUser(user2);
        page2.setTitle("Test page title 2");
        page2.setContent("Test Content 2");

        page2 = pageDao.addPage(page2);

        // Get All Pages from DB
        List<Page> pages = pageDao.getAllPages();

        // Assert
        assertEquals(2, pages.size());
        assertTrue(pages.contains(page));
        assertTrue(pages.contains(page2));

    }

    @Test
    public void testEditPage() {
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

        // Create Page + Add to DB
        Page page = new Page();
        page.setUser(user);
        page.setTitle("Test page title");
        page.setContent("Test Content");

        page = pageDao.addPage(page);

        // Get Page from DB
        Page fromDao = pageDao.getPageById(page.getStaticPageId());

        assertEquals(page, fromDao);

        // Edit both pages
        page.setTitle("New page title test");
        pageDao.editPage(page);

        assertNotEquals(page, fromDao);

        // get page from Dao
        fromDao = pageDao.getPageById(page.getStaticPageId());

        assertEquals(page, fromDao);

    }

    @Test
    public void testDeletePage() {
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

        // Create Page + Add to DB
        Page page = new Page();
        page.setUser(user);
        page.setTitle("Test page title");
        page.setContent("Test Content");

        page = pageDao.addPage(page);

        // Get Page from DB
        Page fromDao = pageDao.getPageById(page.getStaticPageId());

        assertEquals(page, fromDao);
        pageDao.deletePage(page.getStaticPageId());

        fromDao = pageDao.getPageById(page.getStaticPageId());
        assertNull(fromDao);

    }
}
