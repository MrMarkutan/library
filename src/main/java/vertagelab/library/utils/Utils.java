package vertagelab.library.utils;

import vertagelab.library.exception.BookNotFoundException;
import vertagelab.library.exception.UserNotFoundException;

public class Utils {
    public static UserNotFoundException userNotFound(int userId) {
        return new UserNotFoundException("User #" + userId + " was not found.");
    }
    public static BookNotFoundException bookNotFound(int bookId) {
        return new BookNotFoundException("Book #" + bookId + " was not found.");
    }
    public static BookNotFoundException bookNotFound(String title) {
        return new BookNotFoundException("Book \"" + title + "\" was not found.");
    }
}
