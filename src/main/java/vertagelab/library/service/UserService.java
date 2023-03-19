package vertagelab.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vertagelab.library.entity.Book;
import vertagelab.library.entity.User;
import vertagelab.library.repository.BookRepository;
import vertagelab.library.repository.UserRepository;

import java.util.List;

import static vertagelab.library.utils.Utils.bookNotFound;
import static vertagelab.library.utils.Utils.userNotFound;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> saveUsers(List<User> users) {
        return userRepository.saveAll(users);
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> userNotFound(userId));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public String deleteUser(int userId) {
        userRepository.deleteById(userId);
        return "User #" + userId + " was deleted.";
    }

    public User updateUser(int userId, User updatedUser) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> userNotFound(userId));

        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getBookList() != null) {
            existingUser.setBookList(updatedUser.getBookList());
        }
        return userRepository.save(existingUser);
    }

    public String addBookToUser(int userId, int bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> userNotFound(userId));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> bookNotFound(bookId));
        if (book.isAvailable()) {
            user.addBook(book);
            userRepository.save(user);
            return user.getName() + " took \"" + book.getTitle() + "\".";
        } else {
            return "Book \"" + book.getTitle() + "\" is not available.";
        }
    }

    public String returnBookFromUser(int userId, int bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> userNotFound(userId));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> bookNotFound(bookId));
        user.removeBook(book);
        userRepository.save(user);

        return "\"" + book.getTitle() + "\" was returned by " + user.getName();
    }

}