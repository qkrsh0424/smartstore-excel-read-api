package com.example.demo.controller.router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterController {
    @GetMapping("/")
    public String IndexPage(){
        return "index.html";
    }
}
