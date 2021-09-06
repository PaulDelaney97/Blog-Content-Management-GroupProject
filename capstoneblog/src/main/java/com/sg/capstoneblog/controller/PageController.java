/**
 *@author
 *date:
 *purpose:
 */
package com.sg.capstoneblog.controller;

import com.sg.capstoneblog.dao.PageDao;
import com.sg.capstoneblog.dao.UserDao;
import com.sg.capstoneblog.dto.Page;
import java.util.HashSet;
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
public class PageController {

    @Autowired
    PageDao pageDao;

    @Autowired
    UserDao userDao;

    Set<ConstraintViolation<Page>> violations = new HashSet<>();

    @GetMapping("/page")
    public String displayPage(Integer id, Model model) {
        model.addAttribute("pages", pageDao.getAllPages());
        model.addAttribute("page", pageDao.getPageById(id));
        return "page";
    }

    @GetMapping("/owner/editPages")
    public String editPages(Model model) {
        model.addAttribute("pages", pageDao.getAllPages());
        model.addAttribute("errors", violations);
        return "editPages";
    }

    @GetMapping("/owner/addPage")
    public String addPage(Model model) {
        model.addAttribute("errors", violations);
        return "addPage";
    }

    @PostMapping("/owner/addPage")
    public String performAddPage(Page page, HttpServletRequest request) {
        page.setUser(userDao.getUserByEmail(
                request.getUserPrincipal().getName()));

        Validator validate
                = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(page);

        if (violations.isEmpty()) {
            pageDao.addPage(page);
            return "redirect:/owner/editPages";
        } else {
            return "redirect:/owner/addPage";
        }
    }

    @GetMapping("/owner/editPage")
    public String editPage(int id, Model model) {
        final Page page = pageDao.getPageById(id);
        model.addAttribute("page", page);
        model.addAttribute("errors", violations);
        return "editPage";
    }

    @PostMapping("/owner/editPage")
    public String performEditPage(Page page, HttpServletRequest request) {
        final Page originalPage = pageDao.getPageById(page.getStaticPageId());
        page.setUser(originalPage.getUser());

        Validator validate
                = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(page);

        if (violations.isEmpty()) {
            pageDao.editPage(page);
            return "redirect:/owner/editPages";
        } else {
            return "redirect:/owner/editPage?id=" + page.getStaticPageId();
        }
    }

    @GetMapping("/owner/deletePage")
    public String deletePage(Integer id) {
        pageDao.deletePage(id);
        return "redirect:/owner/editPages";
    }

}
