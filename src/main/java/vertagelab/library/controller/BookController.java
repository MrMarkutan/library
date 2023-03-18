package vertagelab.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vertagelab.library.entity.Book;
import vertagelab.library.exception.BookNotFoundException;
import vertagelab.library.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Book> addBooks(@RequestBody List<Book> books) {
        return bookService.saveBooks(books);
    }

    @GetMapping("/all")
    public List<Book> findAllBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable int bookId) {
        return bookService.getBookById(bookId);
    }
    @GetMapping("/findByTitle/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    @DeleteMapping("/{bookId}/delete")
    public String deleteBook(@PathVariable int bookId) {
        return bookService.deleteBook(bookId);
    }

    @PutMapping("/{bookId}/update")
    public Book updateBook(@PathVariable int bookId, @RequestBody Book book) {
        return bookService.updateBook(bookId, book);
    }
}
