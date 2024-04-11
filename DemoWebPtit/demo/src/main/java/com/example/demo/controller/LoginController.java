package com.example.demo.controller;

import com.example.demo.models.LoginModel;
import com.example.demo.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private LoginRepository loginRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password, Model model) {
        LoginModel login = loginRepository.findByUsername(username);
        if (login != null && login.getPassword().equals(password)) {
            return "redirect:/listTest";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}
