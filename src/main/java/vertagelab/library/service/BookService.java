package vertagelab.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vertagelab.library.entity.Book;
import vertagelab.library.exception.BookNotFoundException;
import vertagelab.library.repository.BookRepository;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    public Book saveBook(Book book) {
        return repository.save(book);
    }

    public List<Book> saveBooks(List<Book> books) {
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
        Book existingBook = repository.findById(book.getBook_id())
                .orElseThrow(() ->  new BookNotFoundException(book.getTitle() + "is not found."));

        if (book.getAuthor() != null) {
            existingBook.setAuthor(book.getAuthor());
        }
//        existingBook.setAuthor(book.getAuthor());
        if (book.getTitle() != null) {
            existingBook.setTitle(book.getTitle());
        }
//        existingBook.setTitle(book.getTitle());
        if (book.getUser() != null) {
            existingBook.setUser(book.getUser());
        }
//        existingBook.setUser(book.getUser());
        return repository.save(existingBook);
    }
}
