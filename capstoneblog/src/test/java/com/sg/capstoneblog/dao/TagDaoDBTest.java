package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Tag;
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
public class TagDaoDBTest {

    @Autowired
    TagDao tagDao;

    public TagDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        List<Tag> tags = tagDao.getAllTags();
        for (Tag tag : tags) {
            tagDao.deleteTag(tag.getTagId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAndAddTag() {
        Tag tag = new Tag();
        tag.setName("Test tag name");

        tag = tagDao.addTag(tag);

        Tag fromDao = tagDao.getTagById(tag.getTagId());

        assertEquals(tag, fromDao);
    }

    @Test
    public void testGetAllTags() {
        Tag tag = new Tag();
        tag.setName("Test tag name");
        tag = tagDao.addTag(tag);

        Tag tag2 = new Tag();
        tag2.setName("Test tag name 2");
        tag2 = tagDao.addTag(tag2);

        List<Tag> tags = tagDao.getAllTags();

        assertEquals(2, tags.size());
        assertTrue(tags.contains(tag));
        assertTrue(tags.contains(tag2));

    }

    @Test
    public void testEditTag() {
        Tag tag = new Tag();
        tag.setName("Test tag name");
        tag = tagDao.addTag(tag);

        Tag fromDao = tagDao.getTagById(tag.getTagId());

        assertEquals(tag, fromDao);

        tag.setName("New test tag name");

        tagDao.editTag(tag);

        assertNotEquals(tag, fromDao);

        fromDao = tagDao.getTagById(tag.getTagId());

        assertEquals(tag, fromDao);

    }

    @Test
    public void testDeleteTag() {
        Tag tag = new Tag();
        tag.setName("Test tag name");
        tag = tagDao.addTag(tag);

        Tag fromDao = tagDao.getTagById(tag.getTagId());

        assertEquals(tag, fromDao);

        tagDao.deleteTag(tag.getTagId());

        fromDao = tagDao.getTagById(tag.getTagId());

        assertNull(fromDao);

    }

}
