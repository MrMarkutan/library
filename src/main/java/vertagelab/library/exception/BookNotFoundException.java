package vertagelab.library.exception;

import vertagelab.library.entity.Book;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String s) {
        super(s);
    }
}
