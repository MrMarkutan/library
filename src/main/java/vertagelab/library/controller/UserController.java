package vertagelab.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vertagelab.library.dto.UserRequest;
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
    public ResponseEntity<UserRequest> addUser(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.saveUser(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<UserRequest>> addUsers(@RequestBody List<UserRequest> usersRequest) {
        return new ResponseEntity<>(userService.saveUsers(usersRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRequest> getUserById(@PathVariable int userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserRequest>> findAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        return new ResponseEntity<>(userService.deleteUser(userId),HttpStatus.OK);
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<UserRequest> updateUser(@PathVariable int userId, @RequestBody UserRequest update) {
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
