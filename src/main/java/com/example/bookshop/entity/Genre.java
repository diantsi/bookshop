package com.example.bookshop.entity;

public class Genre {
    private Long id;
    private String name;
    private String description;
    private int numberOfBooks;

    public Genre() {}
    public Genre(Long id, String name, String description, int numberOfBooks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberOfBooks = numberOfBooks;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getNumberOfBooks() { return numberOfBooks; }
    public void setNumberOfBooks(int numberOfBooks) { this.numberOfBooks = numberOfBooks; }

    public Genre orElse(Object o) {
        if(this == null) {
            return null;
        }
        return this;
    }
}
