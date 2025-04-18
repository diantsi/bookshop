package com.example.bookshop.controller;


import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Genre;
import com.example.bookshop.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/book", "/book.html"})
    public String showBooksPage(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book/index";
    }
    @PostMapping("/books")
    public String saveGenre(Book book) {
        bookService.saveBook(book);
        return "redirect:/book";
    }

    @GetMapping("/book_info/{isbn}")
    public String editGenre(@PathVariable String isbn, Model model) {
        Book book = bookService.getByIsbn(isbn);
        model.addAttribute("book", book);
        return "book/book_info";
    }
}
