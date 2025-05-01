package com.example.bookshop.controller;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Author;
import com.example.bookshop.entity.Translator;
import com.example.bookshop.service.AuthorService;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.GenreService;
import com.example.bookshop.service.TranslatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final TranslatorService translatorService;

    public BookController(BookService bookService, GenreService genreService, AuthorService authorService, TranslatorService translatorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.translatorService = translatorService;
    }

    @GetMapping("/books")
    public String searchBooks(@RequestParam(required = false, defaultValue = "") String query, Model model) {
        List<Book> books = bookService.getAllBooks();
        List<Book> filteredBooks = books.stream()
                .filter(book -> book.getName().toLowerCase().contains(query.toLowerCase()) ||
                        book.getISBN().contains(query))
                .collect(Collectors.toList());
        model.addAttribute("books", filteredBooks);
        model.addAttribute("query", query);
        return "review/add_review";
    }

    @GetMapping({"/book", "/book.html"})
    public String showBooksPage(Model model, String keyword) {
        List<Book> books;
        if (keyword == null || keyword.isEmpty()) {
            System.out.println("keyword is null"+keyword+"]");
            books = bookService.getAllBooks();
        } else {
            System.out.println("keyword"+keyword+"]");
            books = bookService.getAllBooksByPrompt(keyword);
            System.out.println("books"+books);
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("books", books);
        return "book/index";
    }

    @PostMapping("/books")
    public String saveBook(@ModelAttribute Book book,
                           @RequestParam("genreIds") List<Long> genreIds,
                           @RequestParam("authorIds") List<Long> authorIds,
                           @RequestParam(value = "translatorIds", required = false) List<Long> translatorIds) {
        List<Genre> genres = genreService.getGenresByIds(genreIds);
        List<Author> authors = authorService.getAuthorsByIds(authorIds);
        List<Translator> translators = translatorIds != null ? translatorService.getTranslatorsByIds(translatorIds) : new ArrayList<>();
        System.out.println("Genre IDs: " + genreIds);
        System.out.println("Author IDs: " + authorIds);
        System.out.println("Translator IDs: " + translatorIds);
        book.setGenres(genres);
        book.setAuthors(authors);
        book.setTranslators(translators);
        bookService.saveBook(book);
        return "redirect:/book";
    }

    @GetMapping("/add_book")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("translators", translatorService.getAllTranslators());
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
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("translators", translatorService.getAllTranslators());
        model.addAttribute("selectedGenreIds", book.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toList()));
        model.addAttribute("selectedAuthorIds", book.getAuthors().stream()
                .map(Author::getId)
                .collect(Collectors.toList()));
        model.addAttribute("selectedTranslatorIds", book.getTranslators().stream()
                .map(Translator::getId)
                .collect(Collectors.toList()));
        return "book/edit_book";
    }

    @RequestMapping(value = "/book/update/{isbn}", method = RequestMethod.POST)
    public String save(@ModelAttribute Book book,
                       @RequestParam("genreIds") String[] genreIdStrings,
                       @RequestParam("authorIds") String[] authorIdStrings,
                       @RequestParam(value = "translatorIds", required = false) String[] translatorIdStrings,
                       @PathVariable String isbn) {
        List<Long> genreIds = Arrays.stream(genreIdStrings)
                .map(Long::parseLong)
                .collect(Collectors.toList());
        List<Long> authorIds = Arrays.stream(authorIdStrings)
                .map(Long::parseLong)
                .collect(Collectors.toList());
        List<Long> translatorIds = translatorIdStrings != null ?
                Arrays.stream(translatorIdStrings)
                        .map(Long::parseLong)
                        .collect(Collectors.toList()) : new ArrayList<>();

        List<Genre> genres = genreService.getGenresByIds(genreIds);
        List<Author> authors = authorService.getAuthorsByIds(authorIds);
        List<Translator> translators = translatorService.getTranslatorsByIds(translatorIds);
        book.setGenres(genres);
        book.setAuthors(authors);
        book.setTranslators(translators);
        book.setISBN(isbn);

        bookService.updateBook(book);
        return "redirect:/book";
    }

    @GetMapping("/book_info/{isbn}")
    public String infoBook(@PathVariable String isbn, Model model) {
        Book book = bookService.getByIsbn(isbn);
        System.out.println("Book authors: " + book.getAuthors());
        System.out.println("Book translators: " + book.getTranslators());
        model.addAttribute("book", book);
        return "book/book_info";
    }

}