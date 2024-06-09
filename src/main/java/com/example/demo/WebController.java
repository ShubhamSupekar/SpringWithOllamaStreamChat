package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebController {

    private final ApiService apiService;

    @Autowired
    public WebController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/chat")
    public String chat(Model model) {
        return "chatPage";
    }

}