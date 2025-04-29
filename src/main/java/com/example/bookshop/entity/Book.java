package com.example.bookshop.entity;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String ISBN;
    private String image;
    private String name;
    private Integer pages;
    private String cover;
    private String language;
    private Integer year;

    private Float width;
    private Float height;
    private Float weight;
    private Float thickness;

    private Double price;
    private Integer quantity;

    private Boolean adultsOnly;
    private List<Genre> genres = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    private List<Translator> translators = new ArrayList<>();


    public Book() {
    }

    public Book(String ISBN, String image, String name, Integer pages, String cover, String language, Integer year, Float width, Float height, Float weight, Float thickness, Double price, Integer quantity, Boolean adultsOnly) {
        this.ISBN = ISBN;
        this.image = image;
        this.name = name;
        this.pages = pages;
        this.cover = cover;
        this.language = language;
        this.year = year;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.thickness = thickness;
        this.price = price;
        this.quantity = quantity;
        this.adultsOnly = adultsOnly;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Translator> getTranslators() {
        return translators;
    }

    public void setTranslators(List<Translator> translators) {
        this.translators = translators;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getThickness() {
        return thickness;
    }

    public void setThickness(Float thickness) {
        this.thickness = thickness;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getAdultsOnly() {
        return adultsOnly;
    }

    public void setAdultsOnly(Boolean adultsOnly) {
        this.adultsOnly = adultsOnly;
    }


}