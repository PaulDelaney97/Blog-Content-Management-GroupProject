/**
 *@author
 *date:
 *purpose:
 */

package com.sg.capstoneblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping(value={"/index", "/home", "/"})
    public String displayHomePage() {
        return "redirect:/posts";
    }
    
}
