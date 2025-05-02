package com.example.bookshop.controller;


import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.BookInstance;
import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Review;
import com.example.bookshop.service.BookInstanceService;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.ReceiptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookInstanceController {

    private final BookInstanceService bookInstanceService;
    private final ReceiptService receiptService;
    private final BookService bookService;

    public BookInstanceController(BookInstanceService bookInstanceService, ReceiptService receiptService, BookService bookService) {
        this.bookInstanceService = bookInstanceService;
        this.receiptService = receiptService;
        this.bookService = bookService;
    }

    @ModelAttribute("bookcopy")
    public BookInstance getInstance() {
        return new BookInstance();
    }

    @GetMapping({"/bookcopy", "/bookcopy.html"})
    public String showInstancesPage(@RequestParam(value = "available", required = false, defaultValue = "false") boolean available, Model model, String keyISBN) {
        List<BookInstance> instancesInit;
        List<BookInstance> instances = new java.util.ArrayList<>();
        if (available) {
            instancesInit = bookInstanceService.getAvailableBookInstances();
        } else {
            instancesInit = bookInstanceService.getAllBookInstances();
        }

        if (keyISBN != null && !keyISBN.isEmpty()) {
            for (BookInstance instance : instancesInit) {
                if (instance.getISBN().equals(keyISBN)) {
                    instances.add(instance);
                }
            }
        } else{
            instances = instancesInit;
        }

        List<Book> books = bookService.getAllBooks();


        model.addAttribute("keyISBN", keyISBN);
        model.addAttribute("instances", instances);
        model.addAttribute("books", books);


        return "bookcopy/index";
    }


    @PostMapping("/bookcopies")
    public String saveBookInstance(BookInstance bookInstance) {
        bookInstanceService.saveBookInstance(bookInstance);
        return "redirect:/bookcopy";
    }

    @GetMapping("/add_bookcopy")
    public String addBookInstance(Model model) {
        model.addAttribute("bookInstance", new BookInstance());
        model.addAttribute("receipts", receiptService.getAllReceipts());
        model.addAttribute("books", bookService.getAllBooks());
        return "bookcopy/add_bookcopy";
    }

    @GetMapping("/edit_bookcopy/{id}")
    public String editBookInstance(@PathVariable Long id, Model model) {
        BookInstance bookInstance = bookInstanceService.getById(id);
        model.addAttribute("bookcopy", bookInstance);
        model.addAttribute("receipts", receiptService.getAllReceipts());
        model.addAttribute("books", bookService.getAllBooks());
        return "bookcopy/edit_bookcopy";
    }

    @PostMapping("/bookcopy/update/{id}")
    public String updateBookInstance(@ModelAttribute("bookcopy") BookInstance bookInstance, @PathVariable Long id) {
        bookInstance.setId(id);
        bookInstanceService.update(bookInstance);
        return "redirect:/bookcopy";
    }
    @RequestMapping(value = "/bookcopy/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteBookInstance(@PathVariable Long id) {
        bookInstanceService.deleteBookInstance(id);
        return "redirect:/bookcopy";
    }
}
