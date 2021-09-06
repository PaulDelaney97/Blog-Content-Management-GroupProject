/**
 *@author Paul Delaney
 *date:
 *purpose:
 */
package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Category;
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
public class CategoryDaoDB implements CategoryDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Category getCategoryById(int id) {
        try {
            final String GET_CATEGORY_BY_ID = "SELECT * FROM category WHERE categoryId = ?";
            return jdbc.queryForObject(GET_CATEGORY_BY_ID, new CategoryMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Category> getAllCategories() {
        final String GET_ALL_CATEGORIES = "SELECT * FROM category";
        return jdbc.query(GET_ALL_CATEGORIES, new CategoryMapper());

    }

    @Override
    @Transactional
    public Category addCategory(Category category) {
        final String INSERT_CATEGORY = "INSERT INTO category(name, description) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_CATEGORY,
                category.getName(),
                category.getDescription());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        category.setCategoryId(newId);
        return category;

    }

    @Override
    public void editCategory(Category category) {
        final String UPDATE_CATEGORY = "UPDATE category SET name = ?, description = ? "
                + "WHERE categoryId = ?";
        jdbc.update(UPDATE_CATEGORY,
                category.getName(),
                category.getDescription(),
                category.getCategoryId());

    }

    @Override
    @Transactional
    public void deleteCategory(int categoryId) {
        
        final String UPDATE_POST_CATEGORY 
                = "UPDATE post SET categoryId = NULL WHERE categoryId = ?";
        jdbc.update(UPDATE_POST_CATEGORY, categoryId);

        final String DELETE_CATEGORY = "DELETE FROM category WHERE categoryId = ?";
        jdbc.update(DELETE_CATEGORY, categoryId);

    }

    public static final class CategoryMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int index) throws SQLException {
            Category category = new Category();
            category.setCategoryId(rs.getInt("categoryId"));
            category.setName(rs.getString("name"));
            category.setDescription(rs.getString("description"));

            return category;
        }
    }
}
