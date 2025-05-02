package com.example.bookshop.service;

import com.example.bookshop.dao.BookDao;
import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.BookInstance;
import com.example.bookshop.entity.Receipt;
import org.springframework.stereotype.Service;
import com.example.bookshop.dao.ReceiptDao;
import com.example.bookshop.entity.BookInstance;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReceiptService {

    private final ReceiptDao receiptDao;
    private final BookDao bookDao;

    public ReceiptService(ReceiptDao receiptDao, BookDao bookDao) {
        this.receiptDao = receiptDao;
        this.bookDao = bookDao;
    }

    public List<Receipt> getAllReceipts() {
        return receiptDao.findAll();
    }
    public void saveReceipt(Receipt receipt) {
        receiptDao.saveReceipt(receipt);
    }

    public Receipt getById(Long id) {
        return receiptDao.findById(id);
    }
    public void update(Receipt receipt) {
        receiptDao.updateReceipt(receipt);
    }
    public void delete(Long id) {
        receiptDao.deleteReceipt(id);
    }

    public double calculateTotalPrice(List<BookInstance> instances) {
        double total = 0.0;
        for (BookInstance instance : instances) {
            Book book = bookDao.findByIsbn(instance.getISBN());
            System.out.println("Book ISBN: " + instance.getISBN() + ", Book Price: " + (book != null ? book.getPrice() : "Not Found"));
            if (book != null) {
                total += book.getPrice();
            }
        }
        return total;
    }

    public List<Receipt> getReceiptsByDateRange(LocalDateTime starttime, LocalDateTime endtime) {
        return receiptDao.findByDateRange(starttime, endtime);
    }

}
