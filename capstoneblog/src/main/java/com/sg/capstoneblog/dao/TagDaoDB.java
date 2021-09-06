/**
 *@author patricia andrianambinintsoa
 *
 *
 */
package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Post;
import com.sg.capstoneblog.dto.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TagDaoDB implements TagDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Tag getTagById(int tagId) {
        try {
            final String GET_TAG_BY_ID = "SELECT * FROM tag WHERE tagId = ?";
            return jdbc.queryForObject(GET_TAG_BY_ID, new TagMapper(), tagId);
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public Tag getTagByName(String name) {
        try {
            final String GET_TAG_BY_NAME = "SELECT * FROM tag WHERE name = ?";
            return jdbc.queryForObject(GET_TAG_BY_NAME, new TagMapper(), name);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Tag> getAllTags() {
        final String GET_ALL_TAGS = "SELECT * FROM tag";
        List<Tag> tags = jdbc.query(GET_ALL_TAGS, new TagMapper());
        return tags;
    }

    @Override
    public List<Tag> getTagsFromPost(Post post) {
        final String SELECT_TAG_FROM_POST = "SELECT * FROM postTag WHERE tagId = ?";
        List<Tag> tags = jdbc.query(SELECT_TAG_FROM_POST, new TagMapper());
        return tags;
    }

    @Override
    @Transactional
    public Tag addTag(Tag tag) {
        final String INSERT_TAG = "INSERT INTO tag(name) VALUES (?)";
        jdbc.update(INSERT_TAG, tag.getName());
        int newTagId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        tag.setTagId(newTagId);
        return tag;
    }

    @Override
    public void editTag(Tag tag) {
        final String UPDATE_TAG = "UPDATE tag SET name = ? WHERE tagId = ?";
        jdbc.update(UPDATE_TAG, tag.getName(), tag.getTagId());
    }

    @Override
    public void deleteTag(int tagId) {
        final String DELETE_TAG = "DELETE FROM tag WHERE tagId = ?";
        jdbc.update(DELETE_TAG, tagId);
    }

    public static final class TagMapper implements RowMapper<Tag> {

        public Tag mapRow(ResultSet rs, int index) throws SQLException {
            Tag tag = new Tag();
            tag.setTagId(rs.getInt("tagId"));
            tag.setName(rs.getString("name"));
            return tag;
        }
    }

}
