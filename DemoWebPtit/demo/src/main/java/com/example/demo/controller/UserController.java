package com.example.demo.controller;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository uRepo;
    @GetMapping({"/showUsers", "listUser"})
    public ModelAndView showList(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        ModelAndView mav = new ModelAndView(("list-users"));
        List<User> list = uRepo.findAll();

        Pageable pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "id");
        Page<User> userPage = uRepo.findAll(pageable);

        mav.addObject("userPage", userPage);
        mav.addObject("users", userPage.getContent());
        return mav;
    }

    @GetMapping("/addUserForm")
    public ModelAndView addUserForm() {
        ModelAndView mav = new ModelAndView("add-user-form");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user) {
        uRepo.save(user);
        return "redirect:/listUser";
    }

    @GetMapping("/showUpdateUserForm")
    public ModelAndView showUpdateForm(@RequestParam Long userId) {
        ModelAndView mav = new ModelAndView("add-user-form");
        User user = uRepo.findById(userId).get();
        mav.addObject("user", user);
        return mav;
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam Long userId) {
        uRepo.deleteById(userId);
        return "redirect:/listUser";
    }
}
