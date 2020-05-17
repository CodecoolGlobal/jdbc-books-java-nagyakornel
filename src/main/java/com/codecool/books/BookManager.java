package com.codecool.books;

import com.codecool.books.model.AuthorDao;
import com.codecool.books.model.Book;
import com.codecool.books.model.BookDao;
import com.codecool.books.view.UserInterface;

public class BookManager extends Manager {
    BookDao bookDao;
    AuthorDao authorDao;

    public BookManager(UserInterface ui, BookDao bookDao, AuthorDao authorDao) {
        super(ui);
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

    @Override
    protected void add() {
        int authorId = ui.readInt("Author ID", 0);
        String title = ui.readString("Title", "X");
        bookDao.add(new Book(authorDao.get(authorId), title));
    }

    @Override
    protected String getName() {
        return "Book Manager";
    }

    @Override
    protected void list() {
        for (Book book: bookDao.getAll()) {
            ui.println(book);
        }
    }

    @Override
    protected void edit() {
        int id = ui.readInt("Book ID", 0);
        Book book = bookDao.get(id);
        if (book == null) {
            ui.println("Book not found!");
            return;
        }
        ui.println(book);

        int authorId = ui.readInt("Author ID", book.getAuthor().getId());
        String title = ui.readString("Last name", book.getTitle());
        book.setAuthor(authorDao.get(authorId));
        book.setTitle(title);
        bookDao.update(book);
    }
}
