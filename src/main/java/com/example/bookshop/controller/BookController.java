package com.example.bookshop.controller;


import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Genre;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;


    public BookController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    // Метод для фільтрації книг
    @GetMapping("/books")
    public String searchBooks(@RequestParam(required = false, defaultValue = "") String query, Model model) {
        List<Book> books = bookService.getAllBooks();
        List<Book> filteredBooks = books.stream()
                .filter(book -> book.getName().toLowerCase().contains(query.toLowerCase()) ||
                        book.getISBN().contains(query))
                .collect(Collectors.toList());
        model.addAttribute("books", filteredBooks);
        model.addAttribute("query", query);  // Повертаємо введене значення для фільтра
        return "review/add_review";  // Ваша HTML сторінка
    }


    @GetMapping({"/book", "/book.html"})
    public String showBooksPage(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book/index";
    }
    @PostMapping("/books")
    public String saveBook(@ModelAttribute Book book,
                           @RequestParam("genreIds") List<Long> genreIds) {
        List<Genre> genres = genreService.getGenresByIds(genreIds);
        book.setGenres(genres);

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
        model.addAttribute("selectedGenreIds", book.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toList()));
        return "book/edit_book";
    }

    @RequestMapping(value = "/book/update/{isbn}", method = RequestMethod.POST)
    public String save(@ModelAttribute Book book,
                       @RequestParam("genreIds") String[] genreIdStrings, // Accept as String array
                       @PathVariable String isbn) {
        List<Long> genreIds = Arrays.stream(genreIdStrings)
                .map(Long::parseLong)
                .collect(Collectors.toList());
        List<Genre> genres = genreService.getGenresByIds(genreIds);
        book.setGenres(genres);
        book.setISBN(isbn);

        bookService.updateBook(book);
        return "redirect:/book";
    }

    @GetMapping("/book_info/{isbn}")
    public String infoBook(@PathVariable String isbn, Model model) {
        Book book = bookService.getByIsbn(isbn);
        model.addAttribute("book", book);
        return "book/book_info";
    }}




