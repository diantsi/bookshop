package com.example.bookshop.controller;

import com.example.bookshop.entity.BookInstance;
import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Receipt;
import com.example.bookshop.entity.Worker;
import com.example.bookshop.security.LoginController;
import com.example.bookshop.service.BookInstanceService;
import com.example.bookshop.service.ClientCardService;
import com.example.bookshop.service.ReceiptService;
import com.example.bookshop.service.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ReceiptController {

    private final ReceiptService receiptService;
    private final ClientCardService clientCardService;

    private final WorkerService workerService;
    private final BookInstanceService bookInstanceService;

    public ReceiptController(ReceiptService receiptService, ClientCardService clientCardService, WorkerService workerService, BookInstanceService bookInstanceService) {
        this.receiptService = receiptService;
        this.clientCardService = clientCardService;
        this.workerService = workerService;
        this.bookInstanceService = bookInstanceService;
    }

    @ModelAttribute("receipt")
    public Receipt getReceipt() {
        return new Receipt();
    }

    @GetMapping({"/receipt", "/receipt.html"})
    public String showCategoriesPage(Model model) {
        List<Receipt> receipts = receiptService.getAllReceipts();
        model.addAttribute("receipts", receipts);
        Optional<Worker> user = workerService.findByTabEmail(LoginController.USER);
        model.addAttribute("tab", user.get().getTabNumber());
        return "receipt/index";
    }

    @GetMapping("/receipt/{id}")
    public String viewReceiptCard(@PathVariable Long id, Model model) {
        Receipt receipt = receiptService.getById(id);
        List<BookInstance> instances = bookInstanceService.getBookInstancesByReceiptId(id);

        model.addAttribute("receipt", receipt);
        model.addAttribute("instances", instances);

        return "receipt/card";
    }

    @PostMapping("/receipts")
    public String saveGenre(Receipt receipt) {
        receiptService.saveReceipt(receipt);
        return "redirect:/receipt";
    }

    @GetMapping("/add_receipt")
    public String addReceipt(Model model) {
        model.addAttribute("receipt", new Receipt());
        model.addAttribute("clientCards", clientCardService.getAllClientCards());
        model.addAttribute("workers", workerService.getAllWorkers());
        return "receipt/add_receipt";
    }

    @GetMapping("/add_my_receipt")
    public String addCashierReceipt(Model model) {
        Receipt receipt = new Receipt();
        Optional<Worker> worker = workerService.findByTabEmail(LoginController.USER);
        receipt.setWorker_id(worker.get().getTabNumber());
        model.addAttribute("receipt", receipt);
        model.addAttribute("clientCards", clientCardService.getAllClientCards());
        model.addAttribute("workers", workerService.getAllWorkers());
        //System.out.println(worker.get().getTabNumber());
        //model.addAttribute("tabNumber", worker.get().getTabNumber());
        return "receipt/add_receipt";
    }

    @GetMapping("/edit_receipt/{id}")
    public String editReceipt(@PathVariable Long id, Model model) {
        Receipt receipt = receiptService.getById(id);
        model.addAttribute("receipt", receipt);
        model.addAttribute("clientCards", clientCardService.getAllClientCards());
        model.addAttribute("workers", workerService.getAllWorkers());
        return "receipt/edit_receipt";
    }

    @PostMapping("/receipt/update/{id}")
    public String updateReceipt(@PathVariable Long id, Receipt receipt) {
        receipt.setId(id);
        receiptService.update(receipt);
        return "redirect:/receipt";
    }
    @RequestMapping(value = "/receipt/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteReceipt(@PathVariable Long id) {
        receiptService.delete(id);
        return "redirect:/receipt";
    }



}
