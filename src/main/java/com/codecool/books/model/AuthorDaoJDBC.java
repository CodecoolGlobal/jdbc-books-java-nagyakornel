package com.codecool.books.model;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoJDBC implements AuthorDao {
    private DataSource dataSource;

    public AuthorDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Author author) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO author(first_name, last_name, birth_date) VALUES (?, ?, ?);");
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setDate(3, author.getBirthDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Author author) {
        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE author SET first_name = ?, last_name = ?, birth_date = ? WHERE id = ?;");
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setDate(3, author.getBirthDate());
            preparedStatement.setInt(4, author.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Author get(int id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM author WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Author author = new Author(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDate("birth_date"));
                author.setId(resultSet.getInt("id"));
                return author;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Author> getAll() {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM author;");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Author> authors = new ArrayList<>();
            while (resultSet.next()) {
                Author author = new Author(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDate("birth_date"));
                author.setId(resultSet.getInt("id"));
                authors.add(author);
            }
            return authors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
