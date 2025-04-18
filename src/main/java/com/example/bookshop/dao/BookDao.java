package com.example.bookshop.dao;


import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Genre;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookDao {

    private final ConnectionDao daoConnection;


    public BookDao(ConnectionDao daoConnection) {
        this.daoConnection = daoConnection;
    }

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book";
        try (Connection connection = daoConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("ISBN"),
                        rs.getString("Image"),
                        rs.getString("Book_name"),
                        rs.getInt("Number_of_pages"),
                        rs.getString("Type_of_cover"),
                        rs.getString("Book_language"),
                        rs.getInt("Year_of_publication"),
                        rs.getFloat("Weight"),
                        rs.getFloat("Height"),
                        rs.getFloat("Width"),
                        rs.getFloat("Thickness"),
                        rs.getDouble("Book_price"),
                        rs.getInt("Number_of_instances"),
                        rs.getBoolean("Adults_only_status")
                );
                book.setGenres(findGenresByIsbn(book.getISBN(), connection));
                book.setAuthors(findAuthorsByIsbn(book.getISBN(), connection));
                book.setTranslators(findTranslatorsByIsbn(book.getISBN(), connection));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }


    public Book findByIsbn(String isbn) {
        String query = "SELECT * FROM book WHERE ISBN = ?";
        Book book = null;

        try (Connection connection = daoConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, isbn);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                book = new Book(
                        rs.getString("ISBN"),
                        rs.getString("Image"),
                        rs.getString("Book_name"),
                        rs.getInt("Number_of_pages"),
                        rs.getString("Type_of_cover"),
                        rs.getString("Book_language"),
                        rs.getInt("Year_of_publication"),
                        rs.getFloat("Weight"),
                        rs.getFloat("Height"),
                        rs.getFloat("Width"),
                        rs.getFloat("Thickness"),
                        rs.getDouble("Book_price"),
                        rs.getInt("Number_of_instances"),
                        rs.getBoolean("Adults_only_status")
                );
                book.setGenres(findGenresByIsbn(book.getISBN(), connection));
                book.setAuthors(findAuthorsByIsbn(book.getISBN(), connection));
                book.setTranslators(findTranslatorsByIsbn(book.getISBN(), connection));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find book", e);
        }
        return book;

    }

    private List<Book.Translator> findTranslatorsByIsbn(String isbn, Connection connection) {
        List<Book.Translator> translators = new ArrayList<>();
        String query = "SELECT t.Id_translator, t.Full_name " +
                "FROM translator t JOIN book_translator bt ON t.Id_translator = bt.Id_translator " +
                "WHERE bt.ISBN = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book.Translator translator = new Book.Translator(
                        rs.getLong("Id_translator"),
                        rs.getString("Full_name")
                );
                translators.add(translator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return translators;

}

    private List<Book.Author> findAuthorsByIsbn(String isbn, Connection connection) {
        List<Book.Author> authors = new ArrayList<>();
        String authorQuery = "SELECT a.Id_author, a.Full_name " +
                "FROM author a JOIN book_author ab ON a.Id_author = ab.Id_author " +
                "WHERE ab.ISBN = ?";
        try (PreparedStatement ps = connection.prepareStatement(authorQuery)) {
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book.Author author = new Book.Author(
                        rs.getLong("Id_author"),
                        rs.getString("Full_name")
                );
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }


    private List<Genre> findGenresByIsbn(String isbn, Connection connection) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        String genreQuery = "SELECT g.Id_genre, g.Genre_name, g.Genre_description, g.Number_of_books " +
                "FROM genre g JOIN genre_book gb ON g.Id_genre = gb.Id_genre " +
                "WHERE gb.Book_ISBN = ?";
        try (PreparedStatement ps = connection.prepareStatement(genreQuery)) {
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Genre genre = new Genre(
                        rs.getLong("Id_genre"),
                        rs.getString("Genre_name"),
                        rs.getString("Genre_description"),
                        rs.getInt("Number_of_books")
                );
                genres.add(genre);
            }
        }
        return genres;
    }


    public void saveBookGenres(String isbn, List<Long> genreIds) {
        String query = "INSERT INTO genre_book (Book_ISBN, Id_genre) VALUES (?, ?)";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            for (Long genreId : genreIds) {
                ps.setString(1, isbn);
                ps.setLong(2, genreId);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save book genres", e);
        }
    }


    public void saveBook(Book book) {
        String query = "INSERT INTO book (ISBN, Image, Book_name, Number_of_pages, Type_of_cover, Book_language, " +
                "Year_of_publication, Weight, Height, Width, Thickness, Book_price, Number_of_instances, Adults_only_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getISBN());
            preparedStatement.setString(2, book.getImage());
            preparedStatement.setString(3, book.getName());
            preparedStatement.setInt(4, book.getPages());
            preparedStatement.setString(5, book.getCover());
            preparedStatement.setString(6, book.getLanguage());
            preparedStatement.setInt(7, book.getYear());
            preparedStatement.setFloat(8, book.getWeight());
            preparedStatement.setFloat(9, book.getHeight());
            preparedStatement.setFloat(10, book.getWidth());
            preparedStatement.setFloat(11, book.getThickness());
            preparedStatement.setDouble(12, book.getPrice());
            preparedStatement.setInt(13, book.getQuantity());
            preparedStatement.setBoolean(14, book.getAdultsOnly());
            preparedStatement.executeUpdate();

            // Save genres if provided
            if (book.getGenres() != null && !book.getGenres().isEmpty()) {
                List<Long> genreIds = book.getGenres().stream()
                        .map(Genre::getId)
                        .collect(Collectors.toList());
                saveBookGenres(book.getISBN(), genreIds);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(String id) {
        String query = "DELETE FROM book WHERE ISBN = ?";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete book", e);
        }
    }
}
