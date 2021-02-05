package vertagelab.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vertagelab.library.entity.User;
import vertagelab.library.service.UserService;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
    @PostMapping("/users")
    public List<User> addUsers(@RequestBody List<User> users) {
        return userService.saveUsers(users);
    }
    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userService.getUsers();
    }
    @GetMapping("/user")
    public User getUserById(@RequestParam(value = "userid") int userid) {
        return userService.getUserById(userid);
    }
    @PutMapping("/deleteUser")
//    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam(value = "userid") int userid) {
        return userService.deleteUser(userid);
    }
    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PutMapping("/takeBook")
    public String takeBookToUser(@RequestParam(value = "userid") int userid,
                                 @RequestParam(value = "bookid") int bookid) {
        return userService.addBookToUser(userid, bookid);
    }

    @PutMapping("/returnBook")
    public String returnBookFromUser(@RequestParam(value = "userid") int userid,
                                     @RequestParam(value = "bookid") int bookid) {
        return userService.returnBookFromUser(userid, bookid);
    }




}
