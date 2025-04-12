package com.example.bookshop.entity;

public class Genre {
    private Long id;
    private String name;
    private String description;
    private int numberOfBooks;

    // конструктори
    public Genre() {}
    public Genre(Long id, String name, String description, int numberOfBooks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberOfBooks = numberOfBooks;
    }

    // геттери та сеттери
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getNumberOfBooks() { return numberOfBooks; }
    public void setNumberOfBooks(int numberOfBooks) { this.numberOfBooks = numberOfBooks; }
}
