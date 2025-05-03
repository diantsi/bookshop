package com.example.bookshop.controller;

import com.example.bookshop.entity.Worker;
import com.example.bookshop.service.WorkerService;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;

@Controller
public class WorkerController {

    public final WorkerService workerService;

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

    @GetMapping("/edit_worker/{tabNumber}")
    public String editWorker(@PathVariable String tabNumber, Model model) {
        Worker worker = workerService.getByTabNumber(tabNumber);
        model.addAttribute("worker", worker);
        model.addAttribute("oldTabNumber", worker.getTabNumber());
        model.addAttribute("newPassword", "");
        return "worker/edit_worker";
    }

    @GetMapping("/worker_info/{tabNumber}")
    public String infoWorker(@PathVariable String tabNumber, Model model) {
        Worker worker = workerService.getByTabNumber(tabNumber);
        model.addAttribute("worker", worker);
        return "worker/worker_info";
    }


    @PostMapping("/workers")
    public String saveWorker(@ModelAttribute("worker") Worker worker, BindingResult result) {
        // Тут виконується валідація
        if(workerService.existsByTabNumber(worker.getTabNumber())){
            result.rejectValue("tabNumber", "error.tabNumber", "Працівник з таким табельним номером вже існує!");
        }

        if (worker.getSalary().compareTo(BigDecimal.ZERO)<0) {
            result.rejectValue("salary", "error.salary", "Зарплата не може бути від’ємною.");
        }
        if (!worker.verifyAge()) {
            result.rejectValue("dateOfBirth", "error.dateOfBirth", "Працівнику має бути не менше 18 років.");
        }

        if (!worker.getPhoneNumber().matches("\\d{10}")) {
            result.rejectValue("phoneNumber", "error.phoneNumber", "Недійсний номер телефону!");
        }


        if (result.hasErrors()) {
            return "worker/add_worker";
        }

        workerService.saveWorker(worker);
        return "redirect:/worker";

    }


    @ModelAttribute("worker")
    public Worker getWorker() {
        return new Worker();
    }

    @RequestMapping(value = "/worker/delete/{tabNumber}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteWorker(@PathVariable String tabNumber) {
        workerService.delete(tabNumber);
        return "redirect:/worker";
    }

    @RequestMapping(value="/worker/edit/{tabNumber}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editWorker(@ModelAttribute("worker") Worker worker, BindingResult result, @RequestParam("oldTabNumber") String oldTabNumber,  @RequestParam("newPassword") String newPassword, Model model) {
        // Тут виконується валідація
        if (!worker.getTabNumber().equals(oldTabNumber)) {
            if(workerService.existsByTabNumber(worker.getTabNumber())){
                result.rejectValue("tabNumber", "error.tabNumber", "Інший працівник з таким табельним номером вже існує!");
            }
        }
        if (worker.getSalary().compareTo(BigDecimal.ZERO)<0) {
            result.rejectValue("salary", "error.salary", "Зарплата не може бути від’ємною.");
        }
        if (!worker.verifyAge()) {
            result.rejectValue("dateOfBirth", "error.dateOfBirth", "Працівнику має бути не менше 18 років.");
        }

        if (!worker.getPhoneNumber().matches("\\d{10}")) {
            result.rejectValue("phoneNumber", "error.phoneNumber", "Недійсний номер телефону!");
        }


        if (result.hasErrors()) {
            model.addAttribute("oldTabNumber", oldTabNumber); // Додати знову в модель
            return "worker/edit_worker"; // Просто повертаємо назву шаблону, не редірект!
        }

        workerService.editWorker(worker, oldTabNumber, newPassword);
        return "redirect:/worker";

    }

}
