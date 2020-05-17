package com.codecool.books.model;

public class Book {
    private Integer id;

    private Author author;
    private String title;

    public Book(Author author, String title) {
        this.author = author;
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Book %d: %s %s: %s",
                id, author.getFirstName(), author.getLastName(), title);
    }
}
