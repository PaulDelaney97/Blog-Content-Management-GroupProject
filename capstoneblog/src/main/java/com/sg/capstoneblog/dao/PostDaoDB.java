/**
 *@author Paul Delaney
 *email: paul.delaney120197@gmail.com
 *date: 26/8/2021
 *purpose:
 */
package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dao.CategoryDaoDB.CategoryMapper;
import com.sg.capstoneblog.dao.TagDaoDB.TagMapper;
import com.sg.capstoneblog.dao.UserDaoDB.UserMapper;
import com.sg.capstoneblog.dto.Category;
import com.sg.capstoneblog.dto.Post;
import com.sg.capstoneblog.dto.Tag;
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
public class PostDaoDB implements PostDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Post getPostById(int id) {
        try {
            final String SELECT_POST_BY_ID = "SELECT * FROM post WHERE postId = ?";
            Post post = jdbc.queryForObject(SELECT_POST_BY_ID, new PostMapper(), id);
            post.setUser(getUserForPost(id));
            post.setCategory(getCategoryForPost(id));
            post.setTags(getTagsForPost(id));
            return post;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    // ------ Private methods are used to set objects in Post object ---------
    private User getUserForPost(int id) {
        final String SELECT_USER_FOR_POST = "SELECT u.* FROM user u "
                + "JOIN post p ON p.userId = u.userId WHERE p.postId = ?";
        return jdbc.queryForObject(SELECT_USER_FOR_POST, new UserMapper(), id);
    }

    private Category getCategoryForPost(int id) {
        try {
            final String SELECT_CATEGORY_FOR_POST = "SELECT c.* FROM category c "
                    + "JOIN post p ON p.categoryId = c.categoryId WHERE p.postId = ?";
            return jdbc.queryForObject(SELECT_CATEGORY_FOR_POST, new CategoryMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Tag> getTagsForPost(int id) {
        final String SELECT_TAGS_FOR_POST = "SELECT t.* FROM tag t "
                + "JOIN postTag pt ON pt.tagId = t.tagId WHERE pt.postId = ?";
        return jdbc.query(SELECT_TAGS_FOR_POST, new TagMapper(), id);
    }
    // -----------------------------------------------------------------------

    @Override
    public List<Post> getAllPosts() {
        final String SELECT_ALL_POSTS = "SELECT * FROM post ORDER BY createdAt DESC";
        List<Post> posts = jdbc.query(SELECT_ALL_POSTS, new PostMapper());
        associateUserCategoryAndTags(posts);
        return posts;
    }

    @Override
    public List<Post> getAllPublishedPosts() {
        final String SELECT_ALL_POSTS = "SELECT * FROM post WHERE published = 1 ORDER BY createdAt DESC";
        List<Post> posts = jdbc.query(SELECT_ALL_POSTS, new PostMapper());
        associateUserCategoryAndTags(posts);
        return posts;
    }

    //------ Method below sets User, Category and Tags for all Posts ---------
    private void associateUserCategoryAndTags(List<Post> posts) {
        for (Post post : posts) {
            post.setUser(getUserForPost(post.getPostId()));
            post.setCategory(getCategoryForPost(post.getPostId()));
            post.setTags(getTagsForPost(post.getPostId()));
        }
    }
    //-------------------------------------------------------------------------

    @Override
    public List<Post> getPostsForCategory(Category cat) {
        final String SELECT_POSTS_FOR_CATEGORY = "SELECT * FROM post WHERE categoryId = ? ORDER BY createdAt DESC";
        List<Post> posts = jdbc.query(SELECT_POSTS_FOR_CATEGORY, 
                new PostMapper(), cat.getCategoryId());
        associateUserCategoryAndTags(posts);
        return posts;
    }
    
    @Override
    public List<Post> getPublishedPostsForCategory(Category cat) {
        final String SELECT_PUBLISHED_POSTS_FOR_CATEGORY 
                = "SELECT * FROM post WHERE categoryId = ? AND published = 1 ORDER BY createdAt DESC";
        List<Post> posts = jdbc.query(SELECT_PUBLISHED_POSTS_FOR_CATEGORY, 
                new PostMapper(), cat.getCategoryId());
        associateUserCategoryAndTags(posts);
        return posts;
    }

    @Override
    public List<Post> getPostsForTag(Tag tag) {
        final String SELECT_POSTS_FOR_TAGS = "SELECT p.* FROM post p JOIN "
                + " postTag pt ON pt.postId = p.postId WHERE pt.tagId = ? ORDER BY createdAt DESC";

        List<Post> posts = jdbc.query(SELECT_POSTS_FOR_TAGS, 
                new PostMapper(), tag.getTagId());
        associateUserCategoryAndTags(posts);
        return posts;
    }
    
    @Override
    public List<Post> getPublishedPostsForTag(Tag tag) {
        final String SELECT_PUB_POSTS_FOR_TAGS 
                = "SELECT p.* FROM post p JOIN postTag pt "
                + "ON pt.postId = p.postId "
                + "WHERE pt.tagId = ? AND published = 1 ORDER BY createdAt DESC";

        List<Post> posts = jdbc.query(SELECT_PUB_POSTS_FOR_TAGS, 
                new PostMapper(), tag.getTagId());
        associateUserCategoryAndTags(posts);
        return posts;
    }

    @Override
    public List<Post> getPostsByCategoryAndTag(Category cat, Tag tag) {
        final String SELECT_POSTS_FOR_TAGS_AND_CATS 
                = "SELECT p.* FROM post p JOIN postTag pt "
                + "ON pt.postId = p.postId "
                + "WHERE p.categoryId = ? AND pt.tagId = ? ORDER BY createdAt DESC";

        List<Post> posts = jdbc.query(SELECT_POSTS_FOR_TAGS_AND_CATS, 
                new PostMapper(), cat.getCategoryId(), tag.getTagId());
        associateUserCategoryAndTags(posts);
        return posts;

    }
    
    @Override
    public List<Post> getPublishedPostsByCategoryAndTag(Category cat, Tag tag) {
        final String SELECT_PUB_POSTS_FOR_TAGS_AND_CATS 
                = "SELECT p.* FROM post p JOIN postTag pt "
                + "ON pt.postId = p.postId "
                + "WHERE p.categoryId = ? AND pt.tagId = ? AND published = 1 ORDER BY createdAt DESC";

        List<Post> posts = jdbc.query(SELECT_PUB_POSTS_FOR_TAGS_AND_CATS, 
                new PostMapper(), cat.getCategoryId(), tag.getTagId());
        associateUserCategoryAndTags(posts);
        return posts;

    }

    @Override
    @Transactional
    public Post addPost(Post post) {
        final String INSERT_POST = "INSERT INTO post(userId, categoryId, title, "
                + "published, createdAt, editedAt, publishedAt, expiryDate, content) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        
        final Integer catId = (post.getCategory() == null) ?
                null : post.getCategory().getCategoryId();

        jdbc.update(INSERT_POST,
                post.getUser().getUserId(),
                catId,
                post.getTitle(),
                post.getPublished(), // DATE TIMES????
                post.getCreatedAt(),
                post.getEditedAt(),
                post.getPublishedAt(),
                post.getExpiryDate(),
                post.getContent());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        post.setPostId(newId);
        insertPostTags(post);
        return post;
    }

    private void insertPostTags(Post post) {

        final String INSERT_POST_TAG = "INSERT INTO postTag(postId, tagId) VALUES(?,?)";

        for (Tag tag : post.getTags()) {
            jdbc.update(INSERT_POST_TAG,
                    post.getPostId(),
                    tag.getTagId());
        }

    }

    @Override
    @Transactional
    public void editPost(Post post) {
        final String UPDATE_POST = "UPDATE post SET userId = ?, categoryId = ?, title = ?,"
                + " published = ?, createdAt = ?, editedAt = ?, publishedAt = ?, expiryDate = ?, content = ? WHERE postId = ?";

        final Integer catId = (post.getCategory() == null) ?
                null : post.getCategory().getCategoryId();
        
        jdbc.update(UPDATE_POST,
                post.getUser().getUserId(),
                catId,
                post.getTitle(),
                post.getPublished(), // DATE TIMES????
                post.getCreatedAt(),
                post.getEditedAt(),
                post.getPublishedAt(),
                post.getExpiryDate(),
                post.getContent(),
                post.getPostId());

        // Delete all the postTag entries for this course then re insert them with
        // updated information.
        final String DELETE_POST_TAG = "DELETE FROM postTag WHERE postId = ?";
        jdbc.update(DELETE_POST_TAG, post.getPostId());
        insertPostTags(post);
    }

    @Override
    @Transactional
    public void deletePost(int id) {
        final String DELETE_POST_TAG = "DELETE FROM postTag WHERE postId = ?";
        jdbc.update(DELETE_POST_TAG, id);

        final String DELETE_POST = "DELETE FROM post WHERE postId = ?";
        jdbc.update(DELETE_POST, id);
    }

    public static final class PostMapper implements RowMapper<Post> {

        @Override
        public Post mapRow(ResultSet rs, int index) throws SQLException {

            Post post = new Post();

            post.setPostId(rs.getInt("postId"));
            post.setTitle(rs.getString("title"));
            post.setPublished(rs.getInt("published"));

            post.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            
            post.setEditedAt(rs.getTimestamp("editedAt").toLocalDateTime());
            
            final var publishedAt = (rs.getTimestamp("publishedAt") == null) ?
                    null : rs.getTimestamp("publishedAt").toLocalDateTime();
            post.setPublishedAt(publishedAt);
            
            final var expiryDate = (rs.getTimestamp("expiryDate") == null) ?
                    null : rs.getTimestamp("expiryDate").toLocalDateTime();
            post.setExpiryDate(expiryDate);
            
            post.setContent(rs.getString("content"));

            // Change SQL to timestamp??
            return post;
        }
    }

}
