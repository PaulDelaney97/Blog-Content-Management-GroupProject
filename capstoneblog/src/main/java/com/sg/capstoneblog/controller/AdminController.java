/**
 *@author 
 *date: 
 *purpose: 
 */

package com.sg.capstoneblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String displayAdminHomepage() {
        return "admin";
    }
}
