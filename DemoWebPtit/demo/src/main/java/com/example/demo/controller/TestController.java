package com.example.demo.controller;

import com.example.demo.repository.TestRepository;
import com.example.demo.models.Test;
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
public class TestController {
    @Autowired
    private TestRepository tRepo;
    @GetMapping({"/showTests", "listTest"})
    public ModelAndView showList(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        ModelAndView mav = new ModelAndView(("list-tests"));
        List<Test> list = tRepo.findAll();

        Pageable pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "id");
        Page<Test> testPage = tRepo.findAll(pageable);

        mav.addObject("testPage", testPage);
        mav.addObject("tests", testPage.getContent());
        return mav;
    }

    @GetMapping("/addTestForm")
    public ModelAndView addTestForm() {
        ModelAndView mav = new ModelAndView("add-test-form");
        Test newTest = new Test();
        mav.addObject("test", newTest);
        return mav;
    }

    @PostMapping("/saveTest")
    public String saveTest(@ModelAttribute Test test) {
        tRepo.save(test);
        return "redirect:/listTest";
    }

    @GetMapping("/showUpdateTestForm")
    public ModelAndView showUpdateForm(@RequestParam Long testId) {
        ModelAndView mav = new ModelAndView("add-test-form");
        Test test = tRepo.findById(testId).get();
        mav.addObject("test", test);
        return mav;
    }

    @GetMapping("/deleteTest")
    public String deleteTest(@RequestParam Long testId) {
        tRepo.deleteById(testId);
        return "redirect:/listTest";
    }

}
