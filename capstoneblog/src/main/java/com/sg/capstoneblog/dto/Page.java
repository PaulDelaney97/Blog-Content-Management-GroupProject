/**
 *@author Ross Mitchell
 *date: 25/08/2021
 *purpose:
 */
package com.sg.capstoneblog.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Page {

    private int staticPageId;
    private User user;
    @NotBlank(message = "Title must not be empty.")
    @Size(max = 48, message = "Title must be less than 128 characters.")
    private String title;
    private String content;

    public int getStaticPageId() {
        return staticPageId;
    }

    public void setStaticPageId(int staticPageId) {
        this.staticPageId = staticPageId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.staticPageId;
        hash = 79 * hash + Objects.hashCode(this.user);
        hash = 79 * hash + Objects.hashCode(this.title);
        hash = 79 * hash + Objects.hashCode(this.content);
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
        final Page other = (Page) obj;
        if (this.staticPageId != other.staticPageId) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }

        return true;
    }

}
