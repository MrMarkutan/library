package vertagelab.library.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vertagelab.library.exception.BookNotFoundException;
import vertagelab.library.exception.UserNotFoundException;

import java.util.Map;

@ControllerAdvice
public class LibraryControllerAdvice {

    @ExceptionHandler({UserNotFoundException.class, BookNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleUserNotFound(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
