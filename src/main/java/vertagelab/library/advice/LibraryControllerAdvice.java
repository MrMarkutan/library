package vertagelab.library.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vertagelab.library.exception.BookNotFoundException;
import vertagelab.library.exception.UserNotFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class LibraryControllerAdvice {

    @ExceptionHandler({UserNotFoundException.class, BookNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleUserNotFound(RuntimeException e) {
        log.error(e.getLocalizedMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintsViolation(ConstraintViolationException e) {
        List<String> violations = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        List<String> violationsForLogs = e.getConstraintViolations().stream()
                .map(ex -> ex.getMessage() + ex.getMessageTemplate() + ex.getPropertyPath())
                .collect(Collectors.toList());
        violationsForLogs.forEach(log::error);

        StringBuilder sb = new StringBuilder();
        violations.forEach(v -> sb.append(v).append("\n"));

        return new ResponseEntity<>(sb.toString(), HttpStatus.NOT_FOUND);
    }
}
