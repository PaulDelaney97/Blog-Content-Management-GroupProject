/**
 *@author Paul Delaney
 *date:
 *purpose:
 */
package com.sg.capstoneblog.controller;

import com.sg.capstoneblog.dao.CategoryDao;
import com.sg.capstoneblog.dao.PageDao;
import com.sg.capstoneblog.dao.UserDao;
import com.sg.capstoneblog.dto.Category;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    UserDao userDao;
    
    @Autowired
    PageDao pageDao;

    Set<ConstraintViolation<Category>> violations = new HashSet<>();

    @GetMapping("/owner/categories")
    public String displayCategories(Model model) {
        List<Category> categories = categoryDao.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("errors", violations);
        model.addAttribute("pages", pageDao.getAllPages());
        return "categories";
    }

    @PostMapping("/owner/categories")
    public String addCategory(HttpServletRequest request) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");

        Category category = new Category();
        category.setName(name);
        category.setDescription(description);

        Validator validate
                = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(category);

        if (violations.isEmpty()) {
            categoryDao.addCategory(category);
            return "redirect:/owner/categories";
        } else {
            return "redirect:/owner/categories";
        }

    }

    @GetMapping("/owner/deleteCategory")
    public String deleteCategory(int id) {
        categoryDao.deleteCategory(id);
        return "redirect:/owner/categories";
    }

}
