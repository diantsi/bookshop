package com.example.bookshop.controller;

import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Receipt;
import com.example.bookshop.service.ReceiptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
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

}
