package vertagelab.library.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vertagelab.library.exception.BookNotFoundException;
import vertagelab.library.exception.UserNotFoundException;

import java.util.Map;

@Slf4j
@ControllerAdvice
public class LibraryControllerAdvice {

    @ExceptionHandler({UserNotFoundException.class, BookNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleUserNotFound(RuntimeException e) {
        log.error(e.getLocalizedMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
