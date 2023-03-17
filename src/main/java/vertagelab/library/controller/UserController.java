package vertagelab.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/all")
    public List<User> addUsers(@RequestBody List<User> users) {
        return userService.saveUsers(users);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/all")
    public List<User> findAllUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/{userId}/delete")
    public String deleteUser(@PathVariable int userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/{userId}/update")
    public User updateUser(@PathVariable int userId, @RequestBody User update) {
        return userService.updateUser(userId, update);
    }

    @PutMapping("/{userId}/takeBook/{bookId}")
    public String takeBookToUser(@PathVariable int userId,
                                 @PathVariable int bookId) {
        return userService.addBookToUser(userId, bookId);
    }

    @PutMapping("/{userId}/returnBook/{bookId}")
    public String returnBookFromUser(@PathVariable int userId,
                                     @PathVariable int bookId) {
        return userService.returnBookFromUser(userId, bookId);
    }

    @ExceptionHandler({UserNotFoundException.class, BookNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
