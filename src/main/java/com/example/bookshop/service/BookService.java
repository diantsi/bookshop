package com.example.bookshop.service;
import com.example.bookshop.dao.BookDao;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Genre;
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
    public void saveBook(Book book) {
        bookDao.saveBook(book);
    }

    public Book getByIsbn(String isbn) {
        return bookDao.findByIsbn(isbn);
    }


}
