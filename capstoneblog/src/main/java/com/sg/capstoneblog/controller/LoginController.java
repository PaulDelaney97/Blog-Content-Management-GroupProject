/**
 *@author 
 *date: 
 *purpose: 
 */

package com.sg.capstoneblog.controller;

import com.sg.capstoneblog.dao.PageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    
    @Autowired
    PageDao pageDao;
    
    @GetMapping("/login")
    public String displayLogin(Model model) {
        model.addAttribute("pages", pageDao.getAllPages());
        return "login";
    }
}
