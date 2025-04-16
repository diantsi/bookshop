package com.example.bookshop.service;


import com.example.bookshop.dao.BookInstanceDao;
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


}
