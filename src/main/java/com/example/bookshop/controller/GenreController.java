package com.example.bookshop.controller;

import com.example.bookshop.entity.Genre;
import com.example.bookshop.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping({"/genre", "/genre.html"})
    public String showCategoriesPage(Model model) {
        List<Genre> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres);
        return "genre/index"; // посилається на genre.index.html
    }


    @GetMapping("/add_genre")
    public String addGenre(Model model) {
        return "genre/add_genre";
    }

    @GetMapping("/edit_genre/{id}")
    public String editGenre(@PathVariable Long id, Model model) {
        Genre genre = genreService.getById(id);
        model.addAttribute("genre", genre);
        return "genre/edit_genre";
    }


    @PostMapping("/genres")
    public String saveGenre(Genre genre) {
        genreService.saveGenre(genre);
        return "redirect:/genre";
    }

    @RequestMapping(value = "/genres/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteGenre(@PathVariable Long id) {
        genreService.delete(id);
        return "redirect:/genre";
    }

    @RequestMapping(value = "/genre/update/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String save(Genre genre, @PathVariable Long id) {
        genreService.saveGenre(genre);
        return "redirect:/genre";
    }
}
