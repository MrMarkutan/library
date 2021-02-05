package vertagelab.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vertagelab.library.entity.Book;
import vertagelab.library.service.BookService;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping("/book")
    public Book addBook(@RequestBody Book book) {
        return service.saveBook(book);
    }

    @PostMapping("/books")
    public List<Book> addBooks(@RequestBody List<Book> books) {
        return service.saveBooks(books);
    }

    @GetMapping("/books")
    public List<Book> findAllBooks() {
        return service.getBooks();
    }

    @GetMapping("/book")
    public Book getBookById(@RequestParam(value = "bookid") int bookid) {
        return service.getBookById(bookid);
    }
    @GetMapping("/findByTitle/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return service.getBookByTitle(title);
    }

    @DeleteMapping("/deleteBook")
    public String deleteBook(@RequestParam(value = "bookid") int bookid) {
        return service.deleteBook(bookid);
    }

    @PutMapping("/updateBook")
    public Book updateBook(@RequestBody Book book) {
        return service.updateBook(book);
    }
}
