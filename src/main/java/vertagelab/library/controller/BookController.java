package vertagelab.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vertagelab.library.entity.Book;
import vertagelab.library.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.CREATED);
    }

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Book>> addBooks(@RequestBody List<Book> books) {
        return new ResponseEntity<>(bookService.saveBooks(books), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAllBooks() {
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable int bookId) {
        return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
    }

    @GetMapping("/findByTitle/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title) {
        return new ResponseEntity<>(bookService.getBookByTitle(title), HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}/delete")
    public ResponseEntity<String> deleteBook(@PathVariable int bookId) {
        return new ResponseEntity<>(bookService.deleteBook(bookId), HttpStatus.OK);
    }

    @PutMapping("/{bookId}/update")
    public ResponseEntity<Book> updateBook(@PathVariable int bookId, @RequestBody Book book) {
        return new ResponseEntity<>(bookService.updateBook(bookId, book), HttpStatus.OK);
    }
}
