package vertagelab.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vertagelab.library.dto.BookRequest;
import vertagelab.library.dto.UserRequest;
import vertagelab.library.entity.Book;
import vertagelab.library.entity.User;
import vertagelab.library.repository.BookRepository;
import vertagelab.library.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static vertagelab.library.utils.Utils.bookNotFound;
import static vertagelab.library.utils.Utils.userNotFound;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public UserRequest saveUser(UserRequest userRequest) {
        User savedUser = userRepository.save(userRequest.toUser());
        return savedUser.toUserRequest();
    }

    public List<UserRequest> saveUsers(List<UserRequest> usersRequest) {

        List<User> users = usersRequest.stream()
                .map(UserRequest::toUser)
                .collect(Collectors.toList());

        List<User> savedUsers = userRepository.saveAll(users);

        return usersListToUserRequestList(savedUsers);
    }

    public UserRequest getUserById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> userNotFound(userId)).toUserRequest();
    }

    public List<UserRequest> getUsers() {
        List<User> users = userRepository.findAll();
        return usersListToUserRequestList(users);
    }

    public String deleteUser(int userId) {
        userRepository.deleteById(userId);
        return "User #" + userId + " was deleted.";
    }

    public UserRequest updateUser(int userId, UserRequest updatedUser) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> userNotFound(userId));

        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getBookList() != null) {
            existingUser.setBookList(
                    updatedUser.getBookList()
                            .stream()
                            .map(BookRequest::toBook)
                            .collect(Collectors.toList()));
        }
        User savedUser = userRepository.save(existingUser);
        return savedUser.toUserRequest();
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

    private static List<UserRequest> usersListToUserRequestList(List<User> users) {
        return users.stream()
                .map(User::toUserRequest)
                .collect(Collectors.toList());
    }
}