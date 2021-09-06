package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Category;
import com.sg.capstoneblog.dto.Post;
import com.sg.capstoneblog.dto.User;
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
public class CategoryDaoDBTest {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    UserDao userDao;

    @Autowired
    PostDao postDao;

    public CategoryDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        // Set up by first deleting all Categorys, Tags, Users and Posts from
        // our Daos.
        List<Category> categories = categoryDao.getAllCategories();
        categories.forEach(cat -> {
            categoryDao.deleteCategory(cat.getCategoryId());
        });

        List<User> users = userDao.getAllUsers();
        users.forEach(user -> {
            userDao.deleteUser(user.getUserId());
        });

        List<Post> posts = postDao.getAllPosts();
        posts.forEach(post -> {
            postDao.deletePost(post.getPostId());
        });
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetCategory() {
        Category category = new Category();
        category.setName("Test name");
        category.setDescription("Test description");

        category = categoryDao.addCategory(category);

        Category fromDao = categoryDao.getCategoryById(category.getCategoryId());

        assertEquals(category, fromDao);
    }

    @Test
    public void testGetAllCategories() {
        Category category = new Category();
        category.setName("Test name");
        category.setDescription("Test description");

        Category category2 = new Category();
        category.setName("Test name 2");
        category.setDescription("Test description 2");

        categoryDao.addCategory(category);
        categoryDao.addCategory(category2);

        List<Category> categories = categoryDao.getAllCategories();

        assertEquals(2, categories.size());
        assertTrue(categories.contains(category));
        assertTrue(categories.contains(category2));
    }

    @Test
    public void testEditCategory() {
        Category category = new Category();
        category.setName("Test name");
        category.setDescription("Test description");

        category = categoryDao.addCategory(category);
        Category fromDao = categoryDao.getCategoryById(category.getCategoryId());

        assertEquals(category, fromDao);

        category.setDescription("New Description");
        categoryDao.editCategory(category);

        assertNotEquals(category, fromDao);

        fromDao = categoryDao.getCategoryById(category.getCategoryId());
        assertEquals(category, fromDao);
    }

    @Test
    public void testDeleteCategory() {
        Category category = new Category();
        category.setName("Test name");
        category.setDescription("Test description");
        category = categoryDao.addCategory(category);

        Category fromDao = categoryDao.getCategoryById(category.getCategoryId());
        assertEquals(category, fromDao);

        categoryDao.deleteCategory(category.getCategoryId());

        fromDao = categoryDao.getCategoryById(category.getCategoryId());
        assertNull(fromDao);

    }

}
