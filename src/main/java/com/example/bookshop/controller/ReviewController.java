package com.example.bookshop.controller;

import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Review;
import com.example.bookshop.service.GenreService;
import com.example.bookshop.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping({"/review", "/review.html"})
    public String showReviewsPage(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "review/index";
    }


    @GetMapping("/add_review")
    public String addReview(Model model) {
        return "review/add_review";
    }

    /*@GetMapping("/edit_genre/{id}")
    public String editReview(@PathVariable Long id, Model model) {
        Genre genre = reviewService.getById(id);
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
    }*/
}
