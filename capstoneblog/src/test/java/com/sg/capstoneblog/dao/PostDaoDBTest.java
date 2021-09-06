package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Category;
import com.sg.capstoneblog.dto.Post;
import com.sg.capstoneblog.dto.Role;
import com.sg.capstoneblog.dto.Tag;
import com.sg.capstoneblog.dto.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
public class PostDaoDBTest {

    @Autowired
    PostDao postDao;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    TagDao tagDao;

    @Autowired
    CategoryDao categoryDao;

    public PostDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        List<Post> posts = postDao.getAllPosts();
        for (Post post : posts) {
            postDao.deletePost(post.getPostId());
        }

        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            userDao.deleteUser(user.getUserId());
        }

        List<Role> roles = roleDao.getAllRoles();
        for (Role role : roles) {
            roleDao.deleteRoleById(role.getRoleId());
        }

        List<Tag> tags = tagDao.getAllTags();
        for (Tag tag : tags) {
            tagDao.deleteTag(tag.getTagId());
        }

        List<Category> categories = categoryDao.getAllCategories();
        categories.forEach(cat -> {
            categoryDao.deleteCategory(cat.getCategoryId());
        });
    }

    @AfterEach
    public void tearDown() {
        List<Post> posts = postDao.getAllPosts();
        for (Post post : posts) {
            postDao.deletePost(post.getPostId());
        }

        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            userDao.deleteUser(user.getUserId());
        }

        List<Role> roles = roleDao.getAllRoles();
        for (Role role : roles) {
            roleDao.deleteRoleById(role.getRoleId());
        }

        List<Tag> tags = tagDao.getAllTags();
        for (Tag tag : tags) {
            tagDao.deleteTag(tag.getTagId());
        }

        List<Category> categories = categoryDao.getAllCategories();
        categories.forEach(cat -> {
            categoryDao.deleteCategory(cat.getCategoryId());
        });
    }

    @Test
    public void testGetAndAddPost() {

        // Create Role + add to Dao
        Role role = new Role();
        role.setName("TEST ROLE");
        role = roleDao.addRole(role);

        // Create User + add to Dao
        User user = new User();
        user.setRole(role);
        user.setFirstName("Test first name");
        user.setLastName("Test Last name");
        user.setEmail("Test email");
        user.setPassword("Test password");
        user = userDao.addUser(user);

        // Create Category  + add to Dao
        Category category = new Category();
        category.setName("Test name");
        category.setDescription("Test description");
        category = categoryDao.addCategory(category);

        // Create Tag + add to Dao
        Tag tag = new Tag();
        tag.setName("Test tag name");
        tag = tagDao.addTag(tag);

        // Create Tag List
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);

        // Create Local times
        LocalDateTime now = LocalDateTime.now().withNano(0);

        // Create Post + add to Dao
        Post post = new Post();
        post.setUser(user);
        post.setCategory(category);
        post.setTags(tags);
        post.setTitle("Test Post Title");
        post.setPublished(1);
        post.setCreatedAt(now);
        post.setEditedAt(now);
        post.setPublishedAt(now);
        post.setExpiryDate(now);
        post.setContent("Test post content");

        post = postDao.addPost(post);

        // Get post from Dao
        Post fromDao = postDao.getPostById(post.getPostId());

        // Assert
        assertEquals(post, fromDao);
    }

    @Test
    public void testGetAllPosts() {
        // Create Role + add to Dao
        Role role = new Role();
        role.setName("TEST ROLE");
        role = roleDao.addRole(role);

        // Create User + add to Dao
        User user = new User();
        user.setRole(role);
        user.setFirstName("Test first name");
        user.setLastName("Test Last name");
        user.setEmail("Test email");
        user.setPassword("Test password");
        user = userDao.addUser(user);

        // Create Category  + add to Dao
        Category category = new Category();
        category.setName("Test name");
        category.setDescription("Test description");
        category = categoryDao.addCategory(category);

        // Create Tag + add to Dao
        Tag tag = new Tag();
        tag.setName("Test tag name");
        tag = tagDao.addTag(tag);

        // Create Tag List
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);

        // Create Local times
        LocalDateTime now = LocalDateTime.now().withNano(0);

        // Create Post + add to Dao
        Post post = new Post();
        post.setUser(user);
        post.setCategory(category);
        post.setTags(tags);
        post.setTitle("Test Post Title");
        post.setPublished(1);
        post.setCreatedAt(now);
        post.setEditedAt(now);
        post.setPublishedAt(now);
        post.setExpiryDate(now);
        post.setContent("Test post content");

        post = postDao.addPost(post);

        // Create a second Post
        // Create Role + add to Dao
        Role role1 = new Role();
        role1.setName("TEST ROLE 1");
        role1 = roleDao.addRole(role);

        // Create User + add to Dao
        User user1 = new User();
        user1.setRole(role);
        user1.setFirstName("Test first name 1");
        user1.setLastName("Test Last name 1");
        user1.setEmail("Test email 1");
        user1.setPassword("Test password 1");
        user1 = userDao.addUser(user1);

        // Create Category  + add to Dao
        Category category1 = new Category();
        category1.setName("Test name");
        category1.setDescription("Test description");
        category1 = categoryDao.addCategory(category1);

        // Create Tag + add to Dao
        Tag tag1 = new Tag();
        tag1.setName("Test tag name");
        tag1 = tagDao.addTag(tag1);

        // Create Tag List
        List<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);

        // Create Post + add to Dao
        Post post1 = new Post();
        post1.setUser(user1);
        post1.setCategory(category1);
        post1.setTags(tags1);
        post1.setTitle("Test Post Title 1");
        post1.setPublished(1);
        post1.setCreatedAt(now);
        post1.setEditedAt(now);
        post1.setPublishedAt(now);
        post1.setExpiryDate(now);
        post1.setContent("Test post content 1");

        post1 = postDao.addPost(post1);

        List<Post> posts = postDao.getAllPosts();

        //Assert
        assertEquals(2, posts.size());
        assertTrue(posts.contains(post));
        assertTrue(posts.contains(post1));
    }

    @Test
    public void testEditPost() {
        // Create Role + add to Dao
        Role role = new Role();
        role.setName("TEST ROLE");
        role = roleDao.addRole(role);

        // Create User + add to Dao
        User user = new User();
        user.setRole(role);
        user.setFirstName("Test first name");
        user.setLastName("Test Last name");
        user.setEmail("Test email");
        user.setPassword("Test password");
        user = userDao.addUser(user);

        // Create Category  + add to Dao
        Category category = new Category();
        category.setName("Test name");
        category.setDescription("Test description");
        category = categoryDao.addCategory(category);

        // Create Tag + add to Dao
        Tag tag = new Tag();
        tag.setName("Test tag name");
        tag = tagDao.addTag(tag);

        // Create Tag List
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);

        // Create Local times
        LocalDateTime now = LocalDateTime.now().withNano(0);

        // Create Post + add to Dao
        Post post = new Post();
        post.setUser(user);
        post.setCategory(category);
        post.setTags(tags);
        post.setTitle("Test Post Title");
        post.setPublished(1);
        post.setCreatedAt(now);
        post.setEditedAt(now);
        post.setPublishedAt(now);
        post.setExpiryDate(now);
        post.setContent("Test post content");

        post = postDao.addPost(post);
        Post fromDao = postDao.getPostById(post.getPostId());
        assertEquals(post, fromDao);

        // Edit post manually
        post.setTitle("New Post Title");
        Tag tag2 = new Tag();
        tag2.setName("Test tag 2");
        tag2 = tagDao.addTag(tag2);
        tags.add(tag2);
        post.setTags(tags);

        // Edit post in DAo
        postDao.editPost(post);

        assertNotEquals(post, fromDao);

        fromDao = postDao.getPostById(post.getPostId());

        assertEquals(post, fromDao);

    }

    @Test
    public void testDeleteCourseById() {
        // Create Role + add to Dao
        Role role = new Role();
        role.setName("TEST ROLE");
        role = roleDao.addRole(role);

        // Create User + add to Dao
        User user = new User();
        user.setRole(role);
        user.setFirstName("Test first name");
        user.setLastName("Test Last name");
        user.setEmail("Test email");
        user.setPassword("Test password");
        user = userDao.addUser(user);

        // Create Category  + add to Dao
        Category category = new Category();
        category.setName("Test name");
        category.setDescription("Test description");
        category = categoryDao.addCategory(category);

        // Create Tag + add to Dao
        Tag tag = new Tag();
        tag.setName("Test tag name");
        tag = tagDao.addTag(tag);

        // Create Tag List
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);

        // Create Local times
        LocalDateTime now = LocalDateTime.now().withNano(0);

        // Create Post + add to Dao
        Post post = new Post();
        post.setUser(user);
        post.setCategory(category);
        post.setTags(tags);
        post.setTitle("Test Post Title");
        post.setPublished(1);
        post.setCreatedAt(now);
        post.setEditedAt(now);
        post.setPublishedAt(now);
        post.setExpiryDate(now);
        post.setContent("Test post content");

        post = postDao.addPost(post);
        Post fromDao = postDao.getPostById(post.getPostId());
        assertEquals(post, fromDao);

        postDao.deletePost(post.getPostId());

        fromDao = postDao.getPostById(post.getPostId());
        assertNull(fromDao);
    }

    @Test
    public void testGetPostsForCategory() {
        // Create Role + add to Dao
        Role role = new Role();
        role.setName("TEST ROLE");
        role = roleDao.addRole(role);

        // Create User + add to Dao
        User user = new User();
        user.setRole(role);
        user.setFirstName("Test first name");
        user.setLastName("Test Last name");
        user.setEmail("Test email");
        user.setPassword("Test password");
        user = userDao.addUser(user);

        // Create Category  + add to Dao
        Category category = new Category();
        category.setName("Test name");
        category.setDescription("Test description");
        category = categoryDao.addCategory(category);

        // Create Tag + add to Dao
        Tag tag = new Tag();
        tag.setName("Test tag name");
        tag = tagDao.addTag(tag);

        // Create Tag List
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);

        // Create Local times
        LocalDateTime now = LocalDateTime.now().withNano(0);

        // Create Post + add to Dao
        Post post = new Post();
        post.setUser(user);
        post.setCategory(category);
        post.setTags(tags);
        post.setTitle("Test Post Title");
        post.setPublished(1);
        post.setCreatedAt(now);
        post.setEditedAt(now);
        post.setPublishedAt(now);
        post.setExpiryDate(now);
        post.setContent("Test post content");

        post = postDao.addPost(post);

        // Create a second Post
        // Create Role + add to Dao
        Role role1 = new Role();
        role1.setName("TEST ROLE 1");
        role1 = roleDao.addRole(role);

        // Create User + add to Dao
        User user1 = new User();
        user1.setRole(role);
        user1.setFirstName("Test first name 1");
        user1.setLastName("Test Last name 1");
        user1.setEmail("Test email 1");
        user1.setPassword("Test password 1");
        user1 = userDao.addUser(user1);

        // Create Category  + add to Dao
        Category category1 = new Category();
        category1.setName("Test name");
        category1.setDescription("Test description");
        category1 = categoryDao.addCategory(category1);

        // Create Tag + add to Dao
        Tag tag1 = new Tag();
        tag1.setName("Test tag name");
        tag1 = tagDao.addTag(tag1);

        // Create Tag List
        List<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);

        // Create Post + add to Dao
        Post post1 = new Post();
        post1.setUser(user1);
        post1.setCategory(category1);
        post1.setTags(tags1);
        post1.setTitle("Test Post Title 1");
        post1.setPublished(1);
        post1.setCreatedAt(now);
        post1.setEditedAt(now);
        post1.setPublishedAt(now);
        post1.setExpiryDate(now);
        post1.setContent("Test post content 1");

        // Create Post + add to Dao
        Post post2 = new Post();
        post2.setUser(user1);
        post2.setCategory(category1);
        post2.setTags(tags1);
        post2.setTitle("Test Post Title 2");
        post2.setPublished(1);
        post2.setCreatedAt(now);
        post2.setEditedAt(now);
        post2.setPublishedAt(now);
        post2.setExpiryDate(now);
        post2.setContent("Test post content 2");

        post1 = postDao.addPost(post1);
        post2 = postDao.addPost(post2);

        List<Post> posts = postDao.getPostsForCategory(category1);
        assertEquals(2, posts.size());
        assertFalse(posts.contains(post));
        assertTrue(posts.contains(post1));
        assertTrue(posts.contains(post2));
    }

    @Test
    public void testGetPostsForTag() {
        // Create Role + add to Dao
        Role role = new Role();
        role.setName("TEST ROLE");
        role = roleDao.addRole(role);

        // Create User + add to Dao
        User user = new User();
        user.setRole(role);
        user.setFirstName("Test first name");
        user.setLastName("Test Last name");
        user.setEmail("Test email");
        user.setPassword("Test password");
        user = userDao.addUser(user);

        // Create Category  + add to Dao
        Category category = new Category();
        category.setName("Test name");
        category.setDescription("Test description");
        category = categoryDao.addCategory(category);

        // Create Tag + add to Dao
        Tag tag = new Tag();
        tag.setName("Test tag name");
        tag = tagDao.addTag(tag);

        // Create Tag + add to Dao
        Tag tag1 = new Tag();
        tag1.setName("Test tag name");
        tag1 = tagDao.addTag(tag1);

        // Create Tag List
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);

        // Create Tag List
        List<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);
        tags1.add(tag);

        // Create Local times
        LocalDateTime now = LocalDateTime.now().withNano(0);

        // Create Post + add to Dao
        Post post = new Post();
        post.setUser(user);
        post.setCategory(category);
        post.setTags(tags);
        post.setTitle("Test Post Title");
        post.setPublished(1);
        post.setCreatedAt(now);
        post.setEditedAt(now);
        post.setPublishedAt(now);
        post.setExpiryDate(now);
        post.setContent("Test post content");

        post = postDao.addPost(post);

        // Create a second Post
        // Create Role + add to Dao
        Role role1 = new Role();
        role1.setName("TEST ROLE 1");
        role1 = roleDao.addRole(role);

        // Create User + add to Dao
        User user1 = new User();
        user1.setRole(role);
        user1.setFirstName("Test first name 1");
        user1.setLastName("Test Last name 1");
        user1.setEmail("Test email 1");
        user1.setPassword("Test password 1");
        user1 = userDao.addUser(user1);

        // Create Category  + add to Dao
        Category category1 = new Category();
        category1.setName("Test name");
        category1.setDescription("Test description");
        category1 = categoryDao.addCategory(category1);

        // Create Post + add to Dao
        Post post1 = new Post();
        post1.setUser(user1);
        post1.setCategory(category1);
        post1.setTags(tags1);
        post1.setTitle("Test Post Title 1");
        post1.setPublished(1);
        post1.setCreatedAt(now);
        post1.setEditedAt(now);
        post1.setPublishedAt(now);
        post1.setExpiryDate(now);
        post1.setContent("Test post content 1");

        // Create Post + add to Dao
        Post post2 = new Post();
        post2.setUser(user1);
        post2.setCategory(category1);
        post2.setTags(tags1);
        post2.setTitle("Test Post Title 2");
        post2.setPublished(1);
        post2.setCreatedAt(now);
        post2.setEditedAt(now);
        post2.setPublishedAt(now);
        post2.setExpiryDate(now);
        post2.setContent("Test post content 2");

        post1 = postDao.addPost(post1);
        post2 = postDao.addPost(post2);

        List<Post> posts = postDao.getPostsForTag(tag1);

        assertEquals(2, posts.size());
        assertFalse(posts.contains(post));
        assertTrue(posts.contains(post1));
        assertTrue(posts.contains(post2));
    }

    @Test
    public void testGetPostsByCategoryAndTag() {
        // Create Role + add to Dao
        Role role = new Role();
        role.setName("TEST ROLE");
        role = roleDao.addRole(role);

        // Create User + add to Dao
        User user = new User();
        user.setRole(role);
        user.setFirstName("Test first name");
        user.setLastName("Test Last name");
        user.setEmail("Test email");
        user.setPassword("Test password");
        user = userDao.addUser(user);

        // Create Category  + add to Dao
        Category category = new Category();
        category.setName("Test name");
        category.setDescription("Test description");
        category = categoryDao.addCategory(category);

        // Create Tag + add to Dao
        Tag tag = new Tag();
        tag.setName("Test tag name");
        tag = tagDao.addTag(tag);

        // Create Tag + add to Dao
        Tag tag1 = new Tag();
        tag1.setName("Test tag name");
        tag1 = tagDao.addTag(tag1);

        // Create Tag List
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);

        // Create Tag List
        List<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);
        tags1.add(tag);

        // Create Local times
        LocalDateTime now = LocalDateTime.now().withNano(0);

        // Create Post + add to Dao
        Post post = new Post();
        post.setUser(user);
        post.setCategory(category);
        post.setTags(tags);
        post.setTitle("Test Post Title");
        post.setPublished(1);
        post.setCreatedAt(now);
        post.setEditedAt(now);
        post.setPublishedAt(now);
        post.setExpiryDate(now);
        post.setContent("Test post content");

        post = postDao.addPost(post);

        // Create a second Post
        // Create Role + add to Dao
        Role role1 = new Role();
        role1.setName("TEST ROLE 1");
        role1 = roleDao.addRole(role);

        // Create User + add to Dao
        User user1 = new User();
        user1.setRole(role);
        user1.setFirstName("Test first name 1");
        user1.setLastName("Test Last name 1");
        user1.setEmail("Test email 1");
        user1.setPassword("Test password 1");
        user1 = userDao.addUser(user1);

        // Create Category  + add to Dao
        Category category1 = new Category();
        category1.setName("Test name");
        category1.setDescription("Test description");
        category1 = categoryDao.addCategory(category1);

        // Create Post + add to Dao
        Post post1 = new Post();
        post1.setUser(user1);
        post1.setCategory(category);
        post1.setTags(tags1);
        post1.setTitle("Test Post Title 1");
        post1.setPublished(1);
        post1.setCreatedAt(now);
        post1.setEditedAt(now);
        post1.setPublishedAt(now);
        post1.setExpiryDate(now);
        post1.setContent("Test post content 1");

        // Create Post + add to Dao
        Post post2 = new Post();
        post2.setUser(user1);
        post2.setCategory(category1);
        post2.setTags(tags1);
        post2.setTitle("Test Post Title 2");
        post2.setPublished(1);
        post2.setCreatedAt(now);
        post2.setEditedAt(now);
        post2.setPublishedAt(now);
        post2.setExpiryDate(now);
        post2.setContent("Test post content 2");

        post1 = postDao.addPost(post1);
        post2 = postDao.addPost(post2);

        List<Post> posts = postDao.getPostsByCategoryAndTag(category, tag1);

        assertEquals(1, posts.size());
        assertTrue(posts.contains(post1));
    }

    @Test
    public void testGetAllPublishedPosts() {
        // Create Role + add to Dao
        Role role = new Role();
        role.setName("TEST ROLE");
        role = roleDao.addRole(role);

        // Create User + add to Dao
        User user = new User();
        user.setRole(role);
        user.setFirstName("Test first name");
        user.setLastName("Test Last name");
        user.setEmail("Test email");
        user.setPassword("Test password");
        user = userDao.addUser(user);

        // Create Category  + add to Dao
        Category category = new Category();
        category.setName("Test name");
        category.setDescription("Test description");
        category = categoryDao.addCategory(category);

        // Create Tag + add to Dao
        Tag tag = new Tag();
        tag.setName("Test tag name");
        tag = tagDao.addTag(tag);

        // Create Tag List
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);

        // Create Local times
        LocalDateTime now = LocalDateTime.now().withNano(0);

        // Create Post + add to Dao
        Post post = new Post();
        post.setUser(user);
        post.setCategory(category);
        post.setTags(tags);
        post.setTitle("Test Post Title");
        post.setPublished(1);
        post.setCreatedAt(now);
        post.setEditedAt(now);
        post.setPublishedAt(now);
        post.setExpiryDate(now);
        post.setContent("Test post content");

        post = postDao.addPost(post);

        // Create a second Post
        // Create Role + add to Dao
        Role role1 = new Role();
        role1.setName("TEST ROLE 1");
        role1 = roleDao.addRole(role);

        // Create User + add to Dao
        User user1 = new User();
        user1.setRole(role);
        user1.setFirstName("Test first name 1");
        user1.setLastName("Test Last name 1");
        user1.setEmail("Test email 1");
        user1.setPassword("Test password 1");
        user1 = userDao.addUser(user1);

        // Create Category  + add to Dao
        Category category1 = new Category();
        category1.setName("Test name");
        category1.setDescription("Test description");
        category1 = categoryDao.addCategory(category1);

        // Create Tag + add to Dao
        Tag tag1 = new Tag();
        tag1.setName("Test tag name");
        tag1 = tagDao.addTag(tag1);

        // Create Tag List
        List<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);

        // Create Post + add to Dao
        Post post1 = new Post();
        post1.setUser(user1);
        post1.setCategory(category1);
        post1.setTags(tags1);
        post1.setTitle("Test Post Title 1");
        post1.setPublished(1);
        post1.setCreatedAt(now);
        post1.setEditedAt(now);
        post1.setPublishedAt(now);
        post1.setExpiryDate(now);
        post1.setContent("Test post content 1");

        // Create Post + add to Dao
        Post post2 = new Post();
        post2.setUser(user1);
        post2.setCategory(category1);
        post2.setTags(tags1);
        post2.setTitle("Test Post Title 2");
        post2.setPublished(0);
        post2.setCreatedAt(now);
        post2.setEditedAt(now);
        post2.setPublishedAt(now);
        post2.setExpiryDate(now);
        post2.setContent("Test post content 2");

        post1 = postDao.addPost(post1);
        post2 = postDao.addPost(post2);

        List<Post> posts = postDao.getAllPublishedPosts();

        // Assert
        assertEquals(2, posts.size());
        assertTrue(posts.contains(post));
        assertTrue(posts.contains(post1));
    }
}
