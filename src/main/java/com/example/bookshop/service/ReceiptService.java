package com.example.bookshop.service;

import com.example.bookshop.entity.Receipt;
import org.springframework.stereotype.Service;
import com.example.bookshop.dao.ReceiptDao;

import java.util.List;

@Service
public class ReceiptService {

    private final ReceiptDao receiptDao;

    public ReceiptService(ReceiptDao receiptDao) {
        this.receiptDao = receiptDao;
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
}
