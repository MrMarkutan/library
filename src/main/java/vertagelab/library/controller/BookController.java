package vertagelab.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vertagelab.library.dto.BookRequest;
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
    public ResponseEntity<BookRequest> addBook(@RequestBody BookRequest bookRequest) {
        return new ResponseEntity<>(bookService.saveBook(bookRequest), HttpStatus.CREATED);
    }

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<BookRequest>> addBooks(@RequestBody List<BookRequest> booksRequest) {
        return new ResponseEntity<>(bookService.saveBooks(booksRequest), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookRequest>> findAllBooks() {
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookRequest> getBookById(@PathVariable int bookId) {
        return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
    }

    @GetMapping("/findByTitle/{title}")
    public ResponseEntity<BookRequest> getBookByTitle(@PathVariable String title) {
        return new ResponseEntity<>(bookService.getBookByTitle(title), HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}/delete")
    public ResponseEntity<String> deleteBook(@PathVariable int bookId) {
        return new ResponseEntity<>(bookService.deleteBook(bookId), HttpStatus.OK);
    }

    @PutMapping("/{bookId}/update")
    public ResponseEntity<BookRequest> updateBook(@PathVariable int bookId, @RequestBody BookRequest bookRequest) {
        return new ResponseEntity<>(bookService.updateBook(bookId, bookRequest), HttpStatus.OK);
    }
}
