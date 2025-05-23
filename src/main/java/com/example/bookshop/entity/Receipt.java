package com.example.bookshop.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Receipt {

    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime time;

    private Double totalPrice;
    private Integer bonuses;
    private String client_id;
    private String worker_full_name;
    private String worker_id;
    private String client_full_name;

    private List<BookInstance> bookInstances = new ArrayList<>();


    public Receipt() {
    }
    public Receipt(Long id, LocalDateTime time, Double totalPrice, Integer bonuses, String client_id, String work_id) {
        this.id = id;
        this.time = time;
        this.totalPrice = totalPrice;
        this.bonuses = bonuses;
        this.client_id = client_id;
        this.worker_id = work_id;
    }

    public List<BookInstance> getBookInstances() {
        return bookInstances;
    }

    public void setBookInstances(List<BookInstance> bookInstances) {
        this.bookInstances = bookInstances;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getBonuses() {
        return bonuses;
    }

    public void setBonuses(Integer bonuses) {
        this.bonuses = bonuses;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String work_id) {
        this.worker_id = work_id;
    }

    public String getWorker_full_name() {
        return worker_full_name;
    }
    public void setWorker_full_name(String worker_full_name) {
        this.worker_full_name = worker_full_name;
    }
    public String getClient_full_name() {
        return client_full_name;
    }
    public void setClient_full_name(String client_full_name) {
        this.client_full_name = client_full_name;
    }

    public String getFormattedDateTime() {
        if (this.time == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return this.time.format(formatter);
    }

    public Receipt orElse(Object o) {
        if(this == null) {
            return null;
        }
        return this;
    }

}
