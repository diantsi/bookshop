package com.example.bookshop.service;

import com.example.bookshop.dao.BookDao;
import com.example.bookshop.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    public List<Book> getAllBooksByPrompt(String prompt) {
        return bookDao.findAllByIsbnOrName(prompt);
    }

    public List<Book> getAllBooksByGenre(Long idGenre) {
        return bookDao.findAllByGenre(idGenre);
    }

    public void saveBook(Book book) {
        if (book.getISBN() == null || book.getISBN().isEmpty()) {
            throw new IllegalArgumentException("ISBN is required");
        }
        bookDao.saveBook(book);
    }

    public void updateBook(Book book) {
        if (book.getISBN() == null || book.getISBN().isEmpty()) {
            throw new IllegalArgumentException("ISBN is required");
        }
        bookDao.updateBook(book);
    }

    public Book getByIsbn(String isbn) {
        return bookDao.findByIsbn(isbn);
    }

    public void deleteByIsbn(String isbn) {
        bookDao.delete(isbn);
    }
}