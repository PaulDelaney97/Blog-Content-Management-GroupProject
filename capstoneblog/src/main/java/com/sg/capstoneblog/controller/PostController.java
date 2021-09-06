package com.sg.capstoneblog.controller;

import java.util.List;
import com.sg.capstoneblog.dao.CategoryDao;
import com.sg.capstoneblog.dao.PageDao;
import com.sg.capstoneblog.dao.PostDao;
import com.sg.capstoneblog.dao.TagDao;
import com.sg.capstoneblog.dao.UserDao;
import com.sg.capstoneblog.dto.Post;
import com.sg.capstoneblog.dto.Tag;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    @Autowired
    PostDao postDao;

    @Autowired
    UserDao userDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    TagDao tagDao;

    @Autowired
    PageDao pageDao;

    Set<ConstraintViolation<Post>> postViolations = new HashSet<>();

    @GetMapping("/posts")
    public String displayPosts(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer tagId, Model model) {
        model.addAttribute("tags", tagDao.getAllTags());
        model.addAttribute("categories", categoryDao.getAllCategories());
        model.addAttribute("pages", pageDao.getAllPages());
        model.addAttribute("posts", filteredPublishedPosts(categoryId, tagId));
        return "posts";
    }

    @GetMapping("/post")
    public String displayPost(Integer id, Model model) {
        model.addAttribute("pages", pageDao.getAllPages());
        final Post post = postDao.getPostById(id);
        if (post.getPublished() == 0) {
            final Authentication auth
                    = SecurityContextHolder.getContext().getAuthentication();
            final boolean isAuthorised
                    = auth.getAuthorities().stream()
                            .anyMatch(r -> {
                                return r.getAuthority().equals("ADMIN")
                                        || r.getAuthority().equals("OWNER");
                            }
                            );
            if (!isAuthorised) {
                return "redirect:/error";
            }
        }
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/admin/editPosts")
    public String editPosts(@RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer tagId, Model model) {
        model.addAttribute("tags", tagDao.getAllTags());
        model.addAttribute("categories", categoryDao.getAllCategories());
        model.addAttribute("pages", pageDao.getAllPages());
        model.addAttribute("posts", filteredPosts(categoryId, tagId));
        return "editPosts";
    }

    @GetMapping("/admin/addPost")
    public String addPost(Model model) {
        model.addAttribute("categories", categoryDao.getAllCategories());
        model.addAttribute("errors", postViolations);
        return "addPost";
    }

    @PostMapping("/admin/addPost")
    public String performAddPost(Post post, HttpServletRequest request) {
        post.setUser(userDao.getUserByEmail(
                request.getUserPrincipal().getName()));

        final int categoryId = Integer.parseInt(
                request.getParameter("categoryId"));
        if (categoryId != -1) {
            post.setCategory(categoryDao.getCategoryById(categoryId));
        }

        post.setTags(tagsStrToListOfTags(request.getParameter("tagsStr")));

        final LocalDateTime currentTime = LocalDateTime.now();
        post.setCreatedAt(currentTime);
        post.setEditedAt(currentTime);

        if (Boolean.parseBoolean(request.getParameter("publishOnSubmit"))) {
            post.setPublished(1);
            post.setPublishedAt(currentTime);
        } else {
            post.setPublished(0);
        }

        if (Boolean.parseBoolean(request.getParameter("hasExpirationTime"))) {
            final LocalDateTime expirationTime
                    = LocalDateTime.
                            parse(request.getParameter("expirationTime"),
                                    DateTimeFormatter.ofPattern(
                                            "yyyy-MM-dd'T'HH:mm"));
            post.setExpiryDate(expirationTime);
        }

        Validator validate
                = Validation.buildDefaultValidatorFactory().getValidator();
        postViolations = validate.validate(post);

        if (postViolations.isEmpty()) {
            postDao.addPost(post);
            return "redirect:/admin/editPosts";
        } else {
            return "redirect:/admin/addPost";
        }

    }

    @GetMapping("/admin/editPost")
    public String editPost(Integer id, Model model) {
        model.addAttribute("categories", categoryDao.getAllCategories());
        final Post post = postDao.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("tagsStr", listOfTagsToTagsStr(post.getTags()));
        model.addAttribute("errors", postViolations);
        return "editPost";
    }

    @PostMapping("/admin/editPost")
    public String performEditPost(Post post, HttpServletRequest request) {
        final Post originalPost = postDao.getPostById(post.getPostId());
        post.setUser(originalPost.getUser());

        final int categoryId = Integer.parseInt(
                request.getParameter("categoryId"));
        if (categoryId != -1) {
            post.setCategory(categoryDao.getCategoryById(categoryId));
        }

        post.setTags(tagsStrToListOfTags(request.getParameter("tagsStr")));

        post.setCreatedAt(originalPost.getCreatedAt());

        final LocalDateTime currentTime = LocalDateTime.now();
        post.setEditedAt(currentTime);

        post.setPublished(originalPost.getPublished());
        post.setPublishedAt(originalPost.getPublishedAt());

        if (Boolean.parseBoolean(request.getParameter("hasExpirationTime"))) {
            final LocalDateTime expirationTime
                    = LocalDateTime.
                            parse(request.getParameter("expirationTime"),
                                    DateTimeFormatter.ofPattern(
                                            "yyyy-MM-dd'T'HH:mm"));
            post.setExpiryDate(expirationTime);
        } else {
            post.setExpiryDate(null);
        }

        Validator validate
                = Validation.buildDefaultValidatorFactory().getValidator();
        postViolations = validate.validate(post);

        if (postViolations.isEmpty()) {
            postDao.editPost(post);
            return "redirect:/admin/editPosts";
        } else {
            return "redirect:/admin/editPost?id=" + post.getPostId();
        }

    }

    @GetMapping("/owner/deletePost")
    public String deletePost(Integer id) {
        postDao.deletePost(id);
        return "redirect:/admin/editPosts";
    }

    @GetMapping("/owner/publishPost")
    public String publishPost(Integer id) {
        final Post post = postDao.getPostById(id);
        if (post != null) {
            post.setPublished(1);
            post.setPublishedAt(LocalDateTime.now());
            postDao.editPost(post);
        }

        return "redirect:/admin/editPosts";
    }

    private List<Tag> tagsStrToListOfTags(String tagsStr) {
        final List<Tag> tags = new ArrayList<>();
        for (String tagStr : tagsStr.split(",")) {
            if (!"".equals(tagStr)) {
                Tag tag = tagDao.getTagByName(tagStr);
                if (tag == null) {
                    tag = new Tag();
                    tag.setName(tagStr);
                    tagDao.addTag(tag);
                }
                tags.add(tag);
            }
        }
        return tags;
    }

    private String listOfTagsToTagsStr(List<Tag> tags) {
        final String tagsStr;
        if (tags != null) {
            tagsStr = String.join(",",
                    tags.stream().map((tag) -> tag.getName())
                            .collect(Collectors.joining(",")));
        } else {
            tagsStr = "";
        }
        return tagsStr;
    }

    private List<Post> filteredPosts(Integer categoryId, Integer tagId) {
        final List<Post> posts;
        if (categoryId == null || categoryId == -1) {
            if (tagId == null || tagId == -1) {
                posts = postDao.getAllPosts();
            } else {
                posts = postDao.getPostsForTag(tagDao.getTagById(tagId));
            }
        } else {
            if (tagId == null || tagId == -1) {
                posts = postDao.getPostsForCategory(categoryDao.getCategoryById(
                        categoryId));
            } else {
                posts = postDao.getPostsByCategoryAndTag(categoryDao.
                        getCategoryById(categoryId), tagDao.getTagById(tagId));
            }
        }

        return posts;
    }

    private List<Post> filteredPublishedPosts(Integer categoryId, Integer tagId) {
        final List<Post> posts;
        if (categoryId == null || categoryId == -1) {
            if (tagId == null || tagId == -1) {
                posts = postDao.getAllPublishedPosts();
            } else {
                posts = postDao.getPublishedPostsForTag(tagDao.getTagById(tagId));
            }
        } else {
            if (tagId == null || tagId == -1) {
                posts = postDao.getPublishedPostsForCategory(categoryDao.getCategoryById(categoryId));
            } else {
                posts = postDao.getPublishedPostsByCategoryAndTag(categoryDao.
                        getCategoryById(categoryId), tagDao.getTagById(tagId));
            }
        }

        return posts;
    }

}
