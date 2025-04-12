package com.example.bookshop.entity;

public class Genre {
    private Long id;
    private String name;

    // конструктори
    public Genre() {}
    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // геттери та сеттери
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
