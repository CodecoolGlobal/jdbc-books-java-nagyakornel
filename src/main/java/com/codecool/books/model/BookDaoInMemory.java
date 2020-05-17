package com.codecool.books.model;
import java.util.*;

public class BookDaoInMemory implements BookDao {
    Map<Integer, Book> books = new HashMap<>();
    int idCounter = 0;

    @Override
    public void add(Book book) {
        book.setId(idCounter);
        idCounter++;
        books.put(book.getId(), book);
    }

    @Override
    public void update(Book book) {
        books.put(book.getId(), book);
    }

    @Override
    public Book get(int id) {
        return books.get(id);
    }

    @Override
    public List<Book> getAll() {
        return new ArrayList<>(books.values());
    }
}
