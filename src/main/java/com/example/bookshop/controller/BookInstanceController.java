package com.example.bookshop.controller;


import com.example.bookshop.entity.BookInstance;
import com.example.bookshop.entity.Genre;
import com.example.bookshop.service.BookInstanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookInstanceController {

    private final BookInstanceService bookInstanceService;

    public BookInstanceController(BookInstanceService bookInstanceService) {
        this.bookInstanceService = bookInstanceService;
    }

    @GetMapping({"/bookcopy", "/bookcopy.html"})
    public String showInstancesPage(Model model) {
        List<BookInstance>instance = bookInstanceService.getAllBookInstances();
        model.addAttribute("instances", instance);
        return "bookcopy/index";
    }


}
