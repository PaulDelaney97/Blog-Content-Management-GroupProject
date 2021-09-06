package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Page;
import com.sg.capstoneblog.dto.Role;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author pauldelaney
 */
@SpringBootTest
public class RoleDaoDBTest {

    @Autowired
    RoleDao roleDao;
    
    @Autowired
    PageDao pageDao;

    public RoleDaoDBTest() {
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
        
        List<Role> roles = roleDao.getAllRoles();
        for (Role role : roles) {
            roleDao.deleteRoleById(role.getRoleId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetRole() {
        Role role = new Role();
        role.setName("TEST");
        role = roleDao.addRole(role);
        Role fromDao = roleDao.getRoleById(role.getRoleId());
        assertEquals(role, fromDao);

    }

    @Test
    public void testGetAllRoles() {
        Role role = new Role();
        role.setName("TEST");
        role = roleDao.addRole(role);

        Role role1 = new Role();
        role1.setName("TEST 1");
        role1 = roleDao.addRole(role1);

        List<Role> roles = roleDao.getAllRoles();

        assertEquals(2, roles.size());
        assertTrue(roles.contains(role));
        assertTrue(roles.contains(role1));
    }

}
