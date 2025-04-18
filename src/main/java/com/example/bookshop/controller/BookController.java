package com.example.bookshop.controller;


import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Genre;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;


    public BookController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }
    @GetMapping({"/book", "/book.html"})
    public String showBooksPage(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book/index";
    }
    @PostMapping("/books")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        return "redirect:/book";
    }

    @GetMapping("/add_book")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("genres", genreService.getAllGenres());
        return "book/add_book";
    }


    @RequestMapping(value = "/book/delete/{isbn}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteBook(@PathVariable String isbn) {
        bookService.deleteByIsbn(isbn);
        return "redirect:/book";
    }


    @GetMapping("/edit_book/{isbn}")
    public String editBook(@PathVariable String isbn, Model model) {
        Book book = bookService.getByIsbn(isbn);
        model.addAttribute("book", book);
        model.addAttribute("genres", genreService.getAllGenres());
        return "book/edit_book";
    }

    @RequestMapping(value = "/book/update/{isbn}", method = RequestMethod.POST)
    public String save(Book book, @PathVariable String isbn) {
        bookService.updateBook(book);
        return "redirect:/book";
    }

    @GetMapping("/book_info/{isbn}")
    public String infoBook(@PathVariable String isbn, Model model) {
        Book book = bookService.getByIsbn(isbn);
        model.addAttribute("book", book);
        return "book/book_info";
    }}




