/**
 *@author 
 *date: 
 *purpose: 
 */

package com.sg.capstoneblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class OwnerController {

    @GetMapping("/owner")
    public String displayOwnerHomepage() {
        return "owner";
    }
}
