package com.codecool.books.model;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoInJBDC implements BookDao {
    private DataSource dataSource;
    private AuthorDaoInJDBC authorDaoInJDBC;

    public BookDaoInJBDC(DataSource dataSource, AuthorDaoInJDBC authorDaoInJDBC) {
        this.dataSource = dataSource;
        this.authorDaoInJDBC = authorDaoInJDBC;
    }

    @Override
    public void add(Book book) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO book(author_id, title) VALUES (?, ?);");
            preparedStatement.setInt(1, book.getAuthor().getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book book) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE book SET author_id = ?, title = ? WHERE id = ?;");
            preparedStatement.setInt(1, book.getAuthor().getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setInt(3, book.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book get(int id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM book WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book(
                        authorDaoInJDBC.get(resultSet.getInt("author_id")),
                        resultSet.getString("title"));
                book.setId(resultSet.getInt("id"));
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getAll() {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM book;");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book(
                        authorDaoInJDBC.get(resultSet.getInt("author_id")),
                        resultSet.getString("title"));
                book.setId(resultSet.getInt("id"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
