package vertagelab.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vertagelab.library.entity.User;
import vertagelab.library.exception.BookNotFoundException;
import vertagelab.library.exception.UserNotFoundException;
import vertagelab.library.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<User>> addUsers(@RequestBody List<User> users) {
        return new ResponseEntity<>(userService.saveUsers(users), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        return new ResponseEntity<>(userService.deleteUser(userId),HttpStatus.OK);
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User update) {
        return new ResponseEntity<>(userService.updateUser(userId, update), HttpStatus.OK);
    }

    @PutMapping("/{userId}/takeBook/{bookId}")
    public ResponseEntity<String> takeBookToUser(@PathVariable int userId,
                                 @PathVariable int bookId) {
        return new ResponseEntity<>(userService.addBookToUser(userId, bookId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/returnBook/{bookId}")
    public ResponseEntity<String> returnBookFromUser(@PathVariable int userId,
                                     @PathVariable int bookId) {
        return new ResponseEntity<>(userService.returnBookFromUser(userId, bookId),HttpStatus.OK);
    }
}
