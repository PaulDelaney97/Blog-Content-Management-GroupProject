package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dao.UserDaoDB.UserMapper;
import com.sg.capstoneblog.dto.Page;
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

/**
 *
 * @author timi
 */
@Repository
public class PageDaoDB implements PageDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Page getPageById(int id) {
        try {
            final String SELECT_PAGE_BY_ID = "SELECT * FROM staticPage WHERE staticPageId = ?";
            final Page page = jdbc.
                    queryForObject(SELECT_PAGE_BY_ID, new PageMapper(), id);
            page.setUser(getUserForPage(id));
            return page;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Page> getAllPages() {
        final String SELECT_ALL_PAGES = "SELECT * FROM staticPage";
        List<Page> pages = jdbc.query(SELECT_ALL_PAGES, new PageMapper());
        associateUsers(pages);
        return pages;
    }

    @Override
    @Transactional
    public Page addPage(Page page) {
        final String INSERT_PAGE = "INSERT INTO staticPage(userId, title, content) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_PAGE,
                page.getUser().getUserId(),
                page.getTitle(),
                page.getContent());

        int newId = jdbc.
                queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        page.setStaticPageId(newId);
        return page;
    }

    @Override
    public void editPage(Page page) {
        final String UPDATE_PAGE = "UPDATE staticPage SET userId = ?, title = ?, "
                + "content = ? WHERE staticPageId = ?";
        jdbc.update(UPDATE_PAGE,
                page.getUser().getUserId(),
                page.getTitle(),
                page.getContent(),
                page.getStaticPageId());
    }

    @Override
    public void deletePage(int id) {
        final String DELETE_PAGE = "DELETE FROM staticPage WHERE staticPageId = ?";
        jdbc.update(DELETE_PAGE, id);
    }

    private User getUserForPage(int id) {
        final String SELECT_USER_FOR_PAGE
                = "SELECT u.* FROM user u "
                + "JOIN staticPage p ON p.userId = u.userId WHERE p.staticPageId = ?";
        return jdbc.queryForObject(SELECT_USER_FOR_PAGE, new UserMapper(), id);
    }

    private void associateUsers(List<Page> pages) {
        for (Page page : pages) {
            page.setUser(getUserForPage(page.getStaticPageId()));
        }
    }

    public static final class PageMapper implements RowMapper<Page> {

        @Override
        public Page mapRow(ResultSet rs, int index) throws SQLException {
            Page page = new Page();
            page.setStaticPageId(rs.getInt("staticPageId"));
            page.setTitle(rs.getString("title"));
            page.setContent(rs.getString("content"));

            return page;
        }
    }

}
