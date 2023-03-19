package vertagelab.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vertagelab.library.dto.BookRequest;
import vertagelab.library.entity.Book;
import vertagelab.library.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

import static vertagelab.library.utils.Utils.bookNotFound;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public BookRequest saveBook(BookRequest bookRequest) {

        Book savedBook = bookRepository.save(bookRequest.toBook());

        return savedBook.toBookRequest();
    }

    public List<BookRequest> saveBooks(List<BookRequest> booksRequest) {
        List<Book> books = booksRequest.stream()
                .map(BookRequest::toBook)
                .collect(Collectors.toList());

        List<Book> savedBooks = bookRepository.saveAll(books);

        return convertToBooksRequestList(savedBooks);
    }

    public List<BookRequest> getBooks() {
        List<Book> books = bookRepository.findAll();
        return convertToBooksRequestList(books);
    }

    public BookRequest getBookById(int bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> bookNotFound(bookId));
        return book.toBookRequest();
    }

    public BookRequest getBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> bookNotFound(title));
        return book.toBookRequest();
    }

    public String deleteBook(int bookId) {
        bookRepository.deleteById(bookId);
        return "Book #" + bookId + " was deleted.";
    }

    public BookRequest updateBook(int bookId, BookRequest bookRequest) {

        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> bookNotFound(bookId));

        Book bookFromRequest = bookRequest.toBook();
        if (bookFromRequest.getAuthor() != null) {
            existingBook.setAuthor(bookFromRequest.getAuthor());
        }
        if (bookFromRequest.getTitle() != null) {
            existingBook.setTitle(bookFromRequest.getTitle());
        }
        if (bookFromRequest.getUser() != null) {
            existingBook.setUser(bookFromRequest.getUser());
        }
        Book savedBook = bookRepository.save(existingBook);
        return savedBook.toBookRequest();
    }

    private List<BookRequest> convertToBooksRequestList(List<Book> savedBooks) {
        return savedBooks.stream()
                .map(Book::toBookRequest)
                .collect(Collectors.toList());
    }
}
