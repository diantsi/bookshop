package com.example.bookshop.entity;

public class Author {

    private Long id;
    private String full_name;


    public Author() {
    }
    public Author(Long id, String full_name) {
        this.id = id;
        this.full_name = full_name;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
