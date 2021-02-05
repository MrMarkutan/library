package vertagelab.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vertagelab.library.entity.Book;
import vertagelab.library.entity.User;
import vertagelab.library.repository.BookRepository;
import vertagelab.library.repository.UserRepository;

import java.util.List;

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

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public String deleteUser(int user_id) {
        userRepository.deleteById(user_id);
        return "User #" + user_id + " was deleted.";
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getUser_id()).orElse(null);
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getBookList() != null) {
            existingUser.setBookList(user.getBookList());
        }
        return userRepository.save(existingUser);
    }

    public String addBookToUser(int user_id, int book_id) {
        User user = userRepository.findById(user_id).orElse(null);
        Book book = bookRepository.findById(book_id).orElse(null);
        if (book.isAvailable()) {
            user.addBook(book);
            userRepository.save(user);
            return user.getName() + " took \"" + book.getTitle() + "\".";
        } else {
            return "Book \"" + book.getTitle() + "\" is not available.";
        }
    }
    public String returnBookFromUser(int user_id,int book_id) {
        User user = userRepository.findById(user_id).orElse(null);
        Book book = bookRepository.findById(book_id).orElse(null);

        user.removeBook(book);
        userRepository.save(user);

        return "\"" + book.getTitle() + "\" was returned by " + user.getName();
    }
}