package com.example.bookshop.service;


import com.example.bookshop.dao.BookDao;
import com.example.bookshop.dao.BookInstanceDao;
import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.BookInstance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookInstanceService {

    private final BookInstanceDao bookInstanceDao;

    public BookInstanceService(BookInstanceDao bookInstanceDao) {
        this.bookInstanceDao = bookInstanceDao;
    }

    public List<BookInstance> getAllBookInstances() {
        return bookInstanceDao.findAll();
    }

    public void saveBookInstance(BookInstance bookInstance) {
        bookInstanceDao.saveBookInstance(bookInstance);
    }
    public void deleteBookInstance(Long id) {
        bookInstanceDao.deleteBookInstance(id);
    }
    public BookInstance getById(Long id) {
        return bookInstanceDao.findById(id);
    }
    public void update(BookInstance bookInstance) {
        if (bookInstance.getId() == null) {
            throw new IllegalArgumentException("BookInstance ID cannot be null for update");
        }
        bookInstanceDao.updateBookInstance(bookInstance);
    }

    public List<BookInstance> getBookInstancesByReceiptId(Long receiptId) {
        return bookInstanceDao.findBookInstancesByReceiptId(receiptId);
    }

    public List<BookInstance> getInstancesByIds(List<Long> ids) {
        return bookInstanceDao.findByIds(ids);
    }

    public void updateAll(List<BookInstance> instances) {
        bookInstanceDao.updateAll(instances);
    }

    public List<BookInstance> getAvailableInstances() {
        return bookInstanceDao.findAvailableInstances();
    }
    public void saveAll(List<BookInstance> instances) {
        bookInstanceDao.saveAll(instances);
    }


}
