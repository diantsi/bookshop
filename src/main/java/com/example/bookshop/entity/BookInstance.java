package com.example.bookshop.entity;

public class BookInstance {

    private Long id;

    private Long receipt_id;
    private String ISBN;

    public BookInstance() {
    }

    public BookInstance(Long id, Long receipt_id, String ISBN) {
        this.id = id;
        this.receipt_id = receipt_id;
        this.ISBN = ISBN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(Long receipt_id) {
        this.receipt_id = receipt_id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
