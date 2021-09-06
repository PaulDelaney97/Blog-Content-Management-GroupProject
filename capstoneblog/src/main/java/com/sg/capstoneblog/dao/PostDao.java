/**
 *@author
 *date:
 *purpose:
 */
package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Category;
import com.sg.capstoneblog.dto.Post;
import com.sg.capstoneblog.dto.Tag;
import java.util.List;

public interface PostDao {

    Post getPostById(int id);

    List<Post> getAllPosts();

    List<Post> getAllPublishedPosts();

    List<Post> getPostsForCategory(Category cat);
    
    List<Post> getPublishedPostsForCategory(Category cat);

    List<Post> getPostsForTag(Tag tag);
    
    List<Post> getPublishedPostsForTag(Tag tag);

    List<Post> getPostsByCategoryAndTag(Category cat, Tag tag);
    
    List<Post> getPublishedPostsByCategoryAndTag(Category cat, Tag tag);

    Post addPost(Post post);

    void editPost(Post post);

    void deletePost(int id);

}
