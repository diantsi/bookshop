package com.example.bookshop.controller;

import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Receipt;
import com.example.bookshop.service.ClientCardService;
import com.example.bookshop.service.ReceiptService;
import com.example.bookshop.service.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReceiptController {

    private final ReceiptService receiptService;
    private final ClientCardService clientCardService;

    private final WorkerService workerService;

    public ReceiptController(ReceiptService receiptService, ClientCardService clientCardService, WorkerService workerService) {
        this.receiptService = receiptService;
        this.clientCardService = clientCardService;
        this.workerService = workerService;
    }

    @GetMapping({"/receipt", "/receipt.html"})
    public String showCategoriesPage(Model model) {
        List<Receipt> receipts = receiptService.getAllReceipts();
        model.addAttribute("receipts", receipts);
        return "receipt/index";
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
