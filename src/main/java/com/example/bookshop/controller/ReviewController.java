package com.example.bookshop.controller;

import com.example.bookshop.dao.WorkerDao;
import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Review;
import com.example.bookshop.entity.Worker;
import com.example.bookshop.security.LoginController;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.GenreService;
import com.example.bookshop.service.ReviewService;
import com.example.bookshop.service.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final WorkerService workerService;
    private final BookService bookService;


    public ReviewController(ReviewService reviewService, WorkerService workerService, BookService bookService) {
        this.reviewService = reviewService;
        this.workerService = workerService;
        this.bookService = bookService;

    }

    @ModelAttribute("review")
    public Review getReview() {
        return new Review();
    }

    @GetMapping({"/review", "/review.html"})
    public String showReviewsPage(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "review/index";
    }


    @GetMapping("/add_review")
    public String addReview(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "review/add_review";
    }

    @RequestMapping(value = "/review/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteReview(@PathVariable Integer id) {
        reviewService.delete(id);
        return "redirect:/review";
    }

    @PostMapping("/reviews")
    public String saveReview(@ModelAttribute("review") Review review, BindingResult result) {
        review.setNumberOfChars(review.getText().length());
        Optional<Worker> worker = workerService.findByTabEmail(LoginController.USER);
        review.setTabNumber(worker.get().getTabNumber());

        if (result.hasErrors()) {
            return "review/add_review";
        }

        reviewService.saveReview(review);
        return "redirect:/review";
    }

    @GetMapping("/edit_review/{id}")
    public String editReview(@PathVariable Integer id, Model model) {
        Review review = reviewService.getById(id);
        model.addAttribute("review", review);
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        System.out.println(review.getBookISBN());
        System.out.println(review.getNumberOfAnswer());
        return "review/edit_review";
    }

    @RequestMapping(value="/review/edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editReview(@ModelAttribute("review") Review review, BindingResult result, Model model) {

        if (result.hasErrors()) {
            List<Book> books = bookService.getAllBooks();
            model.addAttribute("books", books);
            List<Review> reviews = reviewService.getAllReviews();
            model.addAttribute("reviews", reviews);
            return "review/edit_review";
        }

        review.setNumberOfChars(review.getText().length());
        Optional<Worker> worker = workerService.findByTabEmail(LoginController.USER);
        review.setTabNumber(worker.get().getTabNumber());
        reviewService.editReview(review);
        return "redirect:/review";

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
