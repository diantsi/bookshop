package com.example.bookshop.dao;


import com.example.bookshop.entity.Book;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void saveBook(Book book) {
        String query = "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getISBN());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setDouble(3, book.getPrice());
            preparedStatement.setString(4, book.getImage());
            preparedStatement.setInt(5, book.getPages());
            preparedStatement.setString(6, book.getCover());
            preparedStatement.setString(7, book.getLanguage());
            preparedStatement.setInt(8, book.getYear());
            preparedStatement.setFloat(9, book.getWeight());
            preparedStatement.setFloat(10, book.getHeight());
            preparedStatement.setFloat(11, book.getWidth());
            preparedStatement.setFloat(12, book.getThickness());
            preparedStatement.setInt(13, book.getQuantity());
            preparedStatement.setBoolean(14, book.getAdultsOnly());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find book", e);
        }
            return book;

        }
    }
