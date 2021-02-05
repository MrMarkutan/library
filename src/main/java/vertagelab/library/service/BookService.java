package vertagelab.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vertagelab.library.entity.Book;
import vertagelab.library.repository.BookRepository;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    public Book saveBook(Book book) {
        return repository.save(book);
    }

    public List<Book> saveBooks (List<Book> books) {
        return repository.saveAll(books);
    }

    public List<Book> getBooks() {
        return repository.findAll();
    }

    public Book getBookById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Book getBookByTitle(String title) {
        return repository.findByTitle(title);
    }

    public String deleteBook(int id) {
        repository.deleteById(id);
        return "Book #" + id + " was removed.";
    }

    public Book updateBook(Book book) {
        Book existsingBook = repository.findById(book.getBook_id()).orElse(null);
        if (book.getAuthor() != null) {
            existsingBook.setAuthor(book.getAuthor());
        }
//        existsingBook.setAuthor(book.getAuthor());
        if (book.getTitle() != null) {
            existsingBook.setTitle(book.getTitle());
        }
//        existsingBook.setTitle(book.getTitle());
        if (book.getUser() != null) {
        existsingBook.setUser(book.getUser());
        }
//        existsingBook.setUser(book.getUser());
        return repository.save(existsingBook);
    }
}
