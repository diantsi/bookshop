package com.example.bookshop.controller;

import com.example.bookshop.entity.Author;
import com.example.bookshop.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String listAuthors(Model model) {
        var authors = authorService.getAllAuthors();
        Map<Long, Boolean> hasBooksMap = new HashMap<>();
        for (Author author : authors) {
            hasBooksMap.put(author.getId(), authorService.hasBooks(author.getId()));
        }
        model.addAttribute("authors", authors);
        model.addAttribute("hasBooksMap", hasBooksMap);
        return "author/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("author", new Author());
        return "author/add";
    }

    @PostMapping
    public String saveAuthor(@ModelAttribute Author author) {
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "author/edit";
    }

    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute Author author) {
        author.setId(id);
        authorService.updateAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}