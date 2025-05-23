package com.example.bookshop.controller;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Review;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;
    private final BookService bookService;

    public GenreController(GenreService genreService, BookService bookService) {
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @GetMapping({"/genre", "/genre.html"})
    public String showCategoriesPage(Model model) {
        List<Genre> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres);
        return "genre/index";
    }


    @GetMapping("/add_genre")
    public String addGenre(Model model) {
        return "genre/add_genre";
    }

    @GetMapping("/genre_info/{id}")
    public String infoGenre(@PathVariable Long id, Model model) {
        Genre genre = genreService.getById(id);
        model.addAttribute("genre", genre);
        List<Book> books = bookService.getAllBooksByGenre(id);
        model.addAttribute("books", books);
        return "genre/genre_info";
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
