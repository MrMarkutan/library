package vertagelab.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vertagelab.library.entity.Book;
import vertagelab.library.exception.BookNotFoundException;
import vertagelab.library.repository.BookRepository;

import java.util.List;

import static vertagelab.library.utils.Utils.bookNotFound;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> saveBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(int bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> bookNotFound(bookId));
    }

    public Book getBookByTitle(String title) {
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> bookNotFound(title));
    }

    public String deleteBook(int bookId) {
        bookRepository.deleteById(bookId);
        return "Book #" + bookId + " was deleted.";
    }

    public Book updateBook(int bookId, Book book) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> bookNotFound(bookId));

        if (book.getAuthor() != null) {
            existingBook.setAuthor(book.getAuthor());
        }
        if (book.getTitle() != null) {
            existingBook.setTitle(book.getTitle());
        }
        if (book.getUser() != null) {
            existingBook.setUser(book.getUser());
        }
        return bookRepository.save(existingBook);
    }
}
