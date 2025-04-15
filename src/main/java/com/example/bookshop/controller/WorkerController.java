package com.example.bookshop.controller;

import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Worker;
import com.example.bookshop.service.GenreService;
import com.example.bookshop.service.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService service) {
        this.workerService = service;
    }

    @GetMapping({"/worker", "/worker.html"})
    public String showWorkersPage(Model model) {
        List<Worker> workers = workerService.getAllWorkers();
        model.addAttribute("workers", workers);
        return "worker/index"; // посилається на genre.index.html
    }


    @GetMapping("/add_worker")
    public String addWorker(Model model) {
        return "worker/add_worker";
    }


    @PostMapping("/workers")
    public String addWorker(@ModelAttribute("worker") Worker worker, BindingResult result) {
        // Тут виконується валідація

        if(workerService.existsByTabNumber(worker.getTabNumber())){
            result.rejectValue("tabNumber", "error.tabNumber", "Працівник з таким табельним номером вже існує!");
        }

        if (worker.getSalary() < 0) {
            result.rejectValue("salary", "error.salary", "Зарплата не може бути від’ємною.");
        }
        if (worker.calculateAge() < 18) {
            result.rejectValue("dateOfBirth", "error.dateOfBirth", "Працівнику має бути не менше 18 років.");
        }

        if (result.hasErrors()) {
            return "worker/add_worker";
        }

        workerService.saveWorker(worker);
        return "redirect:/worker";

    }

    @ModelAttribute("worker")
    public Worker getWorker() {
        return new Worker();  // Тобі потрібно мати клас Worker з полем tabNumber
    }
/*
    @GetMapping("/edit_genre/{id}")
    public String editGenre(@PathVariable Long id, Model model) {
        Genre genre = workerService.getById(id);
        model.addAttribute("genre", genre);
        return "genre/edit_genre";
    }


    @PostMapping("/genres")
    public String saveGenre(Genre genre) {
        workerService.saveGenre(genre);
        return "redirect:/genre";
    }

    @RequestMapping(value = "/genres/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteGenre(@PathVariable Long id) {
        workerService.delete(id);
        return "redirect:/genre";
    }
 */
}
