package vertagelab.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import vertagelab.library.entity.Book;
import vertagelab.library.entity.User;
import vertagelab.library.service.UserService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;

    User testUser;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setName("Test User Name");
        testUser.setBookList(List.of(
                new Book("First Test Book Title", "First Test Book Author"),
                new Book("Second Test Book Title", "Second Test Book Author")));
    }

    @Test
    void addUser() throws Exception {
        when(userService.saveUser(any())).thenReturn(testUser);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new User())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(testUser.getName()))
                .andExpect(jsonPath("$.bookList", hasSize(2)))
                .andExpect(jsonPath("$.bookList.[0].title").value(testUser.getBookList().get(0).getTitle()))
                .andExpect(jsonPath("$.bookList.[0].author").value(testUser.getBookList().get(0).getAuthor()))
                .andExpect(jsonPath("$.bookList.[1].title").value(testUser.getBookList().get(1).getTitle()))
                .andExpect(jsonPath("$.bookList.[1].author").value(testUser.getBookList().get(1).getAuthor()));

        verify(userService, times(1)).saveUser(any());
    }

    @Test
    void addUsers() throws Exception {
        when(userService.saveUsers(any())).thenReturn(List.of(testUser, testUser));

        mockMvc.perform(post("/user/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(new User(), new User()))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$.[0].name").value(testUser.getName()))
                .andExpect(jsonPath("$.[0].bookList", hasSize(2)))
                .andExpect(jsonPath("$.[0].bookList.[0].title").value(testUser.getBookList().get(0).getTitle()))
                .andExpect(jsonPath("$.[0].bookList.[0].author").value(testUser.getBookList().get(0).getAuthor()))
                .andExpect(jsonPath("$.[0].bookList.[1].title").value(testUser.getBookList().get(1).getTitle()))
                .andExpect(jsonPath("$.[0].bookList.[1].author").value(testUser.getBookList().get(1).getAuthor()))

                .andExpect(jsonPath("$.[1].name").value(testUser.getName()))
                .andExpect(jsonPath("$.[1].bookList", hasSize(2)))
                .andExpect(jsonPath("$.[1].bookList.[0].title").value(testUser.getBookList().get(0).getTitle()))
                .andExpect(jsonPath("$.[1].bookList.[0].author").value(testUser.getBookList().get(0).getAuthor()))
                .andExpect(jsonPath("$.[1].bookList.[1].title").value(testUser.getBookList().get(1).getTitle()))
                .andExpect(jsonPath("$.[1].bookList.[1].author").value(testUser.getBookList().get(1).getAuthor()));

        verify(userService, times(1)).saveUsers(any());
    }

    @Test
    void getUserById() throws Exception {
        when(userService.getUserById(anyInt())).thenReturn(testUser);

        mockMvc.perform(get("/user/500"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(testUser.getName()))
                .andExpect(jsonPath("$.bookList", hasSize(2)))
                .andExpect(jsonPath("$.bookList.[0].title").value(testUser.getBookList().get(0).getTitle()))
                .andExpect(jsonPath("$.bookList.[0].author").value(testUser.getBookList().get(0).getAuthor()))
                .andExpect(jsonPath("$.bookList.[1].title").value(testUser.getBookList().get(1).getTitle()))
                .andExpect(jsonPath("$.bookList.[1].author").value(testUser.getBookList().get(1).getAuthor()));

        verify(userService, times(1)).getUserById(anyInt());
    }

    @Test
    void findAllUsers() throws Exception {
        when(userService.getUsers()).thenReturn(List.of(testUser));

        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].name").value(testUser.getName()))
                .andExpect(jsonPath("$.[0].bookList", hasSize(2)))
                .andExpect(jsonPath("$.[0].bookList.[0].title").value(testUser.getBookList().get(0).getTitle()))
                .andExpect(jsonPath("$.[0].bookList.[0].author").value(testUser.getBookList().get(0).getAuthor()))
                .andExpect(jsonPath("$.[0].bookList.[1].title").value(testUser.getBookList().get(1).getTitle()))
                .andExpect(jsonPath("$.[0].bookList.[1].author").value(testUser.getBookList().get(1).getAuthor()));

        verify(userService, times(1)).getUsers();
    }

    @Test
    void deleteUser() throws Exception {
        when(userService.deleteUser(anyInt())).thenReturn("User was deleted.");

        mockMvc.perform(delete("/user/1/delete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User was deleted."));

        verify(userService, times(1)).deleteUser(anyInt());
    }

    @Test
    void updateUser() throws Exception {
        when(userService.updateUser(anyInt(), any())).thenReturn(testUser);

        mockMvc.perform(put("/user/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new User())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(testUser.getName()))
                .andExpect(jsonPath("$.bookList", hasSize(2)))
                .andExpect(jsonPath("$.bookList.[0].title").value(testUser.getBookList().get(0).getTitle()))
                .andExpect(jsonPath("$.bookList.[0].author").value(testUser.getBookList().get(0).getAuthor()))
                .andExpect(jsonPath("$.bookList.[1].title").value(testUser.getBookList().get(1).getTitle()))
                .andExpect(jsonPath("$.bookList.[1].author").value(testUser.getBookList().get(1).getAuthor()));

        verify(userService, times(1)).updateUser(anyInt(), any());
    }

    @Test
    void takeBookToUser() throws Exception {
        when(userService.addBookToUser(anyInt(), anyInt())).thenReturn("A book was taken by the User.");

        mockMvc.perform(put("/user/1/takeBook/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("A book was taken by the User."));

        verify(userService, times(1)).addBookToUser(anyInt(), anyInt());
    }

    @Test
    void returnBookFromUser() throws Exception {
        when(userService.returnBookFromUser(anyInt(), anyInt())).thenReturn("Book was returned by the User.");

        mockMvc.perform(put("/user/1/returnBook/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Book was returned by the User."));

        verify(userService, times(1)).returnBookFromUser(anyInt(), anyInt());
    }
}