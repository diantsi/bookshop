package com.example.bookshop.controller;

import com.example.bookshop.dao.WorkerDao;
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

    public ReviewController(ReviewService reviewService, WorkerService workerService) {
        this.reviewService = reviewService;
        this.workerService = workerService;
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
        return "review/add_review";
    }

    @RequestMapping(value = "/review/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteReview(@PathVariable Integer id) {
        reviewService.delete(id);
        return "redirect:/review";
    }

    @PostMapping("/reviews")
    public String saveReview(@ModelAttribute("review") Review review, BindingResult result) {
        // Тут виконується валідація
        /*if(reviewService.existsByTabId(review.getId())){
            result.rejectValue("id", "error.id", "Відгук з таким номером вже існує!");
        }*/

        /*if (review.getSalary().compareTo(BigDecimal.ZERO)<0) {
            result.rejectValue("salary", "error.salary", "Зарплата не може бути від’ємною.");
        }
        if (worker.calculateAge() < 18) {
            result.rejectValue("dateOfBirthString", "error.dateOfBirthString", "Працівнику має бути не менше 18 років.");
        }*/

        review.setNumberOfChars(review.getText().length());
        review.setBookISBN("978-0-7475-3269-9");
        Optional<Worker> worker = workerService.findByTabEmail(LoginController.USER);
        review.setTabNumber(worker.get().getTabNumber());



        if (result.hasErrors()) {
            return "review/add_review";
        }

        reviewService.saveReview(review);
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
