package vertagelab.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vertagelab.library.dto.UserRequest;
import vertagelab.library.entity.Book;
import vertagelab.library.entity.User;
import vertagelab.library.repository.BookRepository;
import vertagelab.library.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    BookRepository bookRepository;
    @InjectMocks
    private UserService userService;
    User testUser;
    Book testBook1, testBook2, extraBook;

    @BeforeEach
    void setUp() {
        testBook1 = new Book();
        testBook1.setTitle("First Test Book Title");
        testBook1.setAuthor("First Test Book Author");

        testBook2 = new Book();
        testBook2.setTitle("First Test Book Title");
        testBook2.setAuthor("First Test Book Author");

        testUser = new User();
        testUser.setName("Test User Name");
        testUser.setBookList(new ArrayList<>(List.of(testBook1, testBook2)));

        testBook1.setUser(testUser);
        testBook2.setUser(testUser);

        testBook1.setAvailable(false);
        testBook2.setAvailable(false);


        extraBook = new Book();
        extraBook.setTitle("Extra test book title");
        extraBook.setAuthor("Extra test book author");

    }

    @Test
    void saveUser() {
        when(userRepository.save(any())).thenReturn(testUser);

        UserRequest savedUser = userService.saveUser(testUser.toUserRequest());

        assertNotNull(savedUser);
        assertEquals(testUser.getName(), savedUser.getName());
        assertEquals(testUser.getBookList().size(), savedUser.getBookList().size());
        assertEquals(testUser.getBookList().get(0).getTitle(), savedUser.getBookList().get(0).getTitle());
        assertEquals(testUser.getBookList().get(0).getAuthor(), savedUser.getBookList().get(0).getAuthor());
//        assertEquals(testUser.getBookList().get(0).getUser(), savedUser.getBookListRequest().get(0).getUser());
        assertFalse(savedUser.getBookList().get(0).isAvailable());

        assertEquals(testUser.getBookList().get(1).getTitle(), savedUser.getBookList().get(1).getTitle());
        assertEquals(testUser.getBookList().get(1).getAuthor(), savedUser.getBookList().get(1).getAuthor());
//        assertEquals(testUser.getBookList().get(1).getUser(), savedUser.getBookListRequest().get(1).getUser());
        assertFalse(savedUser.getBookList().get(1).isAvailable());

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void saveUsers() {
        List<User> listOfTestUsers = new ArrayList<>(List.of(testUser));

        List<UserRequest> listOfTestUserRequests = listOfTestUsers.stream()
                .map(User::toUserRequest)
                .collect(Collectors.toList());

        when(userRepository.saveAll(any())).thenReturn(listOfTestUsers);

        List<UserRequest> savedUsers = userService.saveUsers(new ArrayList<>(listOfTestUserRequests));

        assertNotNull(savedUsers);
        assertEquals(listOfTestUsers.size(), savedUsers.size());
        assertEquals(listOfTestUsers.get(0).getName(), savedUsers.get(0).getName());
        assertEquals(listOfTestUsers.get(0).getBookList().size(),
                savedUsers.get(0).getBookList().size());
        assertEquals(listOfTestUsers.get(0).getBookList().get(0).getTitle(),
                savedUsers.get(0).getBookList().get(0).getTitle());
        assertEquals(listOfTestUsers.get(0).getBookList().get(0).getAuthor(),
                savedUsers.get(0).getBookList().get(0).getAuthor());
//        assertEquals(listOfTestUsers.get(0).getBookList().get(0).getUser(),
//                savedUsers.get(0).getBookListRequest().get(0).getUser());
        assertFalse(listOfTestUsers.get(0).getBookList().get(0).isAvailable());

        verify(userRepository, times(1)).saveAll(any());
    }

    @Test
    void getUserById() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));

        User userFromRepository = userRepository.findById(1).get();

        assertNotNull(userFromRepository);

        assertEquals(testUser.getName(), userFromRepository.getName());
        assertEquals(testUser.getBookList().size(), userFromRepository.getBookList().size());

        assertEquals(testUser.getBookList().get(0).getAuthor(),
                userFromRepository.getBookList().get(0).getAuthor());
        assertEquals(testUser.getBookList().get(0).getTitle(),
                userFromRepository.getBookList().get(0).getTitle());
        assertEquals(testUser.getBookList().get(0).getUser(),
                userFromRepository.getBookList().get(0).getUser());
        assertFalse(userFromRepository.getBookList().get(0).isAvailable());

        assertEquals(testUser.getBookList().get(1).getAuthor(),
                userFromRepository.getBookList().get(1).getAuthor());
        assertEquals(testUser.getBookList().get(1).getTitle(),
                userFromRepository.getBookList().get(1).getTitle());
        assertEquals(testUser.getBookList().get(1).getUser(),
                userFromRepository.getBookList().get(1).getUser());
        assertFalse(userFromRepository.getBookList().get(1).isAvailable());

        verify(userRepository, times(1)).findById(any());
    }

    @Test
    void getUsers() {
        List<User> usersList = List.of(testUser);
        when(userRepository.findAll()).thenReturn(usersList);

        List<UserRequest> usersFromRepository = userService.getUsers();

        assertNotNull(usersFromRepository);
        assertEquals(usersList.size(), usersFromRepository.size());
        assertEquals(usersList.get(0).getName(), usersFromRepository.get(0).getName());
        assertEquals(usersList.get(0).getBookList().size(), usersFromRepository.get(0).getBookList().size());
        assertEquals(usersList.get(0).getBookList().get(0).getTitle(),
                usersFromRepository.get(0).getBookList().get(0).getTitle());
        assertEquals(usersList.get(0).getBookList().get(0).getAuthor(),
                usersFromRepository.get(0).getBookList().get(0).getAuthor());
//        assertEquals(usersList.get(0).getBookList().get(0).getUser(),
//                usersFromRepository.get(0).getBookListRequest().get(0).getUser());
        assertFalse(usersFromRepository.get(0).getBookList().get(0).isAvailable());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void deleteUser() {
        int userId = 100;
        String result = userService.deleteUser(userId);

        assertEquals("User #" + userId + " was deleted.", result);

        verify(userRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void updateUser() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any())).thenReturn(testUser);

        User updatedUser = new User();
        updatedUser.setName("Updated name");
        updatedUser.setBookList(List.of(
                new Book("Update Book Title", "Update Book Author")));

        UserRequest savedUser = userService.updateUser(1, updatedUser.toUserRequest());

        assertNotNull(savedUser);
        assertEquals(updatedUser.getName(), savedUser.getName());
        assertEquals(updatedUser.getBookList().size(), savedUser.getBookList().size());
        assertEquals(updatedUser.getBookList().get(0).getTitle(), savedUser.getBookList().get(0).getTitle());
        assertEquals(updatedUser.getBookList().get(0).getAuthor(), savedUser.getBookList().get(0).getAuthor());
//        assertEquals(updatedUser.getBookList().get(0).getUser(), savedUser.getBookListRequest().get(0).getUser());
        assertTrue(updatedUser.getBookList().get(0).isAvailable());

        verify(userRepository, times(1)).findById(anyInt());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void addBookToUser() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(extraBook));
        when(userRepository.save(any())).thenReturn(testUser);

        String result = userService.addBookToUser(100, 100);

        assertEquals(testUser.getName() + " took \"" + extraBook.getTitle() + "\".", result);

        verify(userRepository, times(1)).findById(anyInt());
        verify(bookRepository, times(1)).findById(anyInt());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void addBookToUserWhenBookIsNotAvailable() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(testBook1));

        String result = userService.addBookToUser(100, 100);

        assertEquals("Book \"" + testBook1.getTitle() + "\" is not available.", result);

        verify(userRepository, times(1)).findById(anyInt());
        verify(bookRepository, times(1)).findById(anyInt());
        verify(userRepository, never()).save(any());
    }

    @Test
    void returnBookFromUser() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(testBook1));
        when(userRepository.save(any())).thenReturn(testUser);

        String result = userService.returnBookFromUser(100, 100);

        assertEquals("\"" + testBook1.getTitle() + "\" was returned by " + testUser.getName(), result);

        verify(userRepository, times(1)).findById(anyInt());
        verify(bookRepository, times(1)).findById(anyInt());
        verify(userRepository, times(1)).save(any());
    }
}