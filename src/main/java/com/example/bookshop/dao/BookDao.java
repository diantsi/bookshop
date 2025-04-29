package com.example.bookshop.dao;


import com.example.bookshop.entity.Author;
import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Translator;
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

    private List<Author> findAuthorsByIsbn(String isbn, Connection connection) throws SQLException {
        List<Author> authors = new ArrayList<>();
        String authorQuery = "SELECT a.Id_author, a.Full_name " +
                "FROM author a JOIN book_author ba ON a.Id_author = ba.Id_author " +
                "WHERE ba.ISBN = ?";
        try (PreparedStatement ps = connection.prepareStatement(authorQuery)) {
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Author author = new Author(
                        rs.getLong("Id_author"),
                        rs.getString("Full_name")
                );
                authors.add(author);
            }
        }
        return authors;
    }

    private List<Translator> findTranslatorsByIsbn(String isbn, Connection connection) throws SQLException {
        List<Translator> translators = new ArrayList<>();
        String translatorQuery = "SELECT t.Id_translator, t.Full_name " +
                "FROM translator t JOIN book_translator bt ON t.Id_translator = bt.Id_translator " +
                "WHERE bt.ISBN = ?";
        try (PreparedStatement ps = connection.prepareStatement(translatorQuery)) {
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Translator translator = new Translator(
                        rs.getLong("Id_translator"),
                        rs.getString("Full_name")
                );
                translators.add(translator);
            }
        }
        return translators;
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

    public void updateBook(Book book) {
        String query = "UPDATE book SET Image = ?, Book_name = ?, Number_of_pages = ?, Type_of_cover = ?, " +
                "Book_language = ?, Year_of_publication = ?, Weight = ?, Height = ?, Width = ?, Thickness = ?, " +
                "Book_price = ?, Number_of_instances = ?, Adults_only_status = ? WHERE ISBN = ?";

        try (Connection connection = daoConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getImage());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setInt(3, book.getPages());
            preparedStatement.setString(4, book.getCover());
            preparedStatement.setString(5, book.getLanguage());
            preparedStatement.setInt(6, book.getYear());
            preparedStatement.setFloat(7, book.getWeight());
            preparedStatement.setFloat(8, book.getHeight());
            preparedStatement.setFloat(9, book.getWidth());
            preparedStatement.setFloat(10, book.getThickness());
            preparedStatement.setDouble(11, book.getPrice());
            preparedStatement.setInt(12, book.getQuantity());
            preparedStatement.setBoolean(13, book.getAdultsOnly() != null ? book.getAdultsOnly() : false);
            preparedStatement.setString(14, book.getISBN());

            preparedStatement.executeUpdate();

            String deleteGenreQuery = "DELETE FROM genre_book WHERE Book_ISBN = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteGenreQuery)) {
                deleteStmt.setString(1, book.getISBN());
                deleteStmt.executeUpdate();
            }

            String deleteAuthorQuery = "DELETE FROM book_author WHERE ISBN = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteAuthorQuery)) {
                deleteStmt.setString(1, book.getISBN());
                deleteStmt.executeUpdate();
            }

            String deleteTranslatorQuery = "DELETE FROM book_translator WHERE ISBN = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteTranslatorQuery)) {
                deleteStmt.setString(1, book.getISBN());
                deleteStmt.executeUpdate();
            }

            if (book.getGenres() != null && !book.getGenres().isEmpty()) {
                saveBookGenres(book.getISBN(), book.getGenres().stream()
                        .map(Genre::getId)
                        .collect(Collectors.toList()));
            }

            if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
                saveBookAuthors(book.getISBN(), book.getAuthors().stream()
                        .map(Author::getId)
                        .collect(Collectors.toList()));
            }

            if (book.getTranslators() != null && !book.getTranslators().isEmpty()) {
                saveBookTranslators(book.getISBN(), book.getTranslators().stream()
                        .map(Translator::getId)
                        .collect(Collectors.toList()));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update book", e);
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
            if(book.getAdultsOnly() == null) {
                preparedStatement.setBoolean(14, false);
            } else {
                preparedStatement.setBoolean(14, book.getAdultsOnly());
            }
            preparedStatement.executeUpdate();

            if (book.getGenres() != null && !book.getGenres().isEmpty()) {
                saveBookGenres(book.getISBN(), book.getGenres().stream()
                        .map(Genre::getId)
                        .collect(Collectors.toList()));
            }
            if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
                saveBookAuthors(book.getISBN(), book.getAuthors().stream()
                        .map(Author::getId)
                        .collect(Collectors.toList()));
            }
            if (book.getTranslators() != null && !book.getTranslators().isEmpty()) {
                saveBookTranslators(book.getISBN(), book.getTranslators().stream()
                        .map(Translator::getId)
                        .collect(Collectors.toList()));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save book", e);
        }
    }

    private void saveBookAuthors(String isbn, List<Long> collect) {
        String query = "INSERT INTO book_author (ISBN, Id_author) VALUES (?, ?)";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            for (Long authorId : collect) {
                ps.setString(1, isbn);
                ps.setLong(2, authorId);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save book authors", e);
        }
    }

    private void saveBookTranslators(String isbn, List<Long> collect) {
        String query = "INSERT INTO book_translator (ISBN, Id_translator) VALUES (?, ?)";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            for (Long translatorId : collect) {
                ps.setString(1, isbn);
                ps.setLong(2, translatorId);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save book translators", e);
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
