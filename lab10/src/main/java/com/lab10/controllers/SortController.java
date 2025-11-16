package com.lab10.controllers;

import org.springframework.ui.Model;
import com.lab10.services.SortService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SortController {

    private final SortService sortService;

    public SortController(SortService sortService) {
        this.sortService = sortService;
    }

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @PostMapping("/sort")
    public String sortArray(@RequestParam("numbers") String numbers, Model model) {
        List<Integer> sorted = sortService.sortNumbers(numbers);
        model.addAttribute("input", numbers);
        model.addAttribute("result", sorted);
        return "result";
    }

    @GetMapping("/back")
    public String goBack() {
        return "redirect:/";
    }
}
