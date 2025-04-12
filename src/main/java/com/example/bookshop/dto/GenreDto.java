package com.example.bookshop.dto;


public class GenreDto {
    private String genreName;
    private String genreDescription;
    private int numberOfBooks;

    // Constructor
    public GenreDto(String genreName, String genreDescription, int numberOfBooks) {
        this.genreName = genreName;
        this.genreDescription = genreDescription;
        this.numberOfBooks = numberOfBooks;
    }

    // Getters and setters
    public String getGenreName() { return genreName; }
    public void setGenreName(String genreName) { this.genreName = genreName; }

    public String getGenreDescription() { return genreDescription; }
    public void setGenreDescription(String genreDescription) { this.genreDescription = genreDescription; }

    public int getNumberOfBooks() { return numberOfBooks; }
    public void setNumberOfBooks(int numberOfBooks) { this.numberOfBooks = numberOfBooks; }

    @Override
    public String toString() {
        return "GenreDto{" +
                "genreName='" + genreName + '\'' +
                ", genreDescription='" + genreDescription + '\'' +
                ", numberOfBooks=" + numberOfBooks +
                '}';
    }
}
