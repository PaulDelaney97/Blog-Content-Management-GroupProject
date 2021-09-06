/**
 *@author
 *date:
 *purpose:
 */
package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Post;
import com.sg.capstoneblog.dto.Tag;
import java.util.List;

public interface TagDao {

    Tag getTagById(int id);
    
    Tag getTagByName(String name);

    List<Tag> getAllTags();

    List<Tag> getTagsFromPost(Post post);

    Tag addTag(Tag tag);

    void editTag(Tag tag);

    void deleteTag(int tagId);

}
