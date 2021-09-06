/**
 *@author Ross Mitchell
 *date: 25/08/2021
 *purpose:
 */
package com.sg.capstoneblog.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Post {

    private int postId;
    private User user;
    private Category category;
    private List<Tag> tags;

    @NotBlank(message = "Post title must not be empty.")
    @Size(max = 48, message = "Title must be less than 48 characters.")
    private String title;
    private int published;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private LocalDateTime publishedAt;

    @Future(message = "Expiry date must be in the future")
    private LocalDateTime expiryDate;
    private String content;

    public Post() {
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.postId;
        hash = 67 * hash + Objects.hashCode(this.user);
        hash = 67 * hash + Objects.hashCode(this.category);
        hash = 67 * hash + Objects.hashCode(this.tags);
        hash = 67 * hash + Objects.hashCode(this.title);
        hash = 67 * hash + Objects.hashCode(this.published);
        hash = 67 * hash + Objects.hashCode(this.createdAt);
        hash = 67 * hash + Objects.hashCode(this.editedAt);
        hash = 67 * hash + Objects.hashCode(this.publishedAt);
        hash = 67 * hash + Objects.hashCode(this.expiryDate);
        hash = 67 * hash + Objects.hashCode(this.content);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Post other = (Post) obj;
        if (this.postId != other.postId) {
            return false;
        }
        if (this.published != other.published) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        /*
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        if (!Objects.equals(this.tags, other.tags)) {
            return false;
        }*/
        if (!Objects.equals(this.createdAt, other.createdAt)) {
            return false;
        }
        if (!Objects.equals(this.editedAt, other.editedAt)) {
            return false;
        }
        if (!Objects.equals(this.publishedAt, other.publishedAt)) {
            return false;
        }
        if (!Objects.equals(this.expiryDate, other.expiryDate)) {
            return false;
        }
        return true;
    }

}
