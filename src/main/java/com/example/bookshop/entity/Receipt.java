package com.example.bookshop.entity;

import java.sql.Timestamp;

public class Receipt {

    private Long id;
    private Timestamp time;
    private Double totalPrice;
    private Integer bonuses;
    private String client_id;
    private String worker_id;


    public Receipt() {
    }
    public Receipt(Long id, Timestamp time, Double totalPrice, Integer bonuses, String client_id, String work_id) {
        this.id = id;
        this.time = time;
        this.totalPrice = totalPrice;
        this.bonuses = bonuses;
        this.client_id = client_id;
        this.worker_id = work_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
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

    public Receipt orElse(Object o) {
        if(this == null) {
            return null;
        }
        return this;
    }

}
