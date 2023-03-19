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
import vertagelab.library.dto.BookRequest;
import vertagelab.library.entity.Book;
import vertagelab.library.exception.BookNotFoundException;
import vertagelab.library.service.BookService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @MockBean
    BookService bookService;
    @Autowired
    private MockMvc mockMvc;

    private Book book;
    private BookRequest bookRequest;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        book = Book.build("Test Title", "Test Author");

        bookRequest = BookRequest.build(book.getTitle(), book.getAuthor(), book.isAvailable());
    }

    @Test
    void addBook() throws Exception {
        when(bookService.saveBook(any())).thenReturn(bookRequest);

        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.available").value(book.isAvailable()));

    }

    @Test
    void addBooks() throws Exception {
        when(bookService.saveBooks(any())).thenReturn(List.of(bookRequest, bookRequest, bookRequest));

        mockMvc.perform(post("/book/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(bookRequest, bookRequest, bookRequest))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));

        verify(bookService, times(1)).saveBooks(any());
    }

    @Test
    void findAllBooks() throws Exception {
        when(bookService.getBooks()).thenReturn(List.of(bookRequest, bookRequest, bookRequest));

        mockMvc.perform(get("/book/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));

        verify(bookService, times(1)).getBooks();
    }

    @Test
    void testGetBookById() throws Exception {
        when(bookService.getBookById(anyInt())).thenReturn(bookRequest);

        mockMvc.perform(get("/book/500"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.available").value(book.isAvailable()));

        verify(bookService, times(1)).getBookById(500);
    }

    @Test
    void getBookByTitle() throws Exception {
        when(bookService.getBookByTitle(anyString())).thenReturn(bookRequest);

        mockMvc.perform(get("/book/findByTitle/testTitle"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(bookRequest.getTitle()))
                .andExpect(jsonPath("$.author").value(bookRequest.getAuthor()))
                .andExpect(jsonPath("$.available").value(bookRequest.isAvailable()));

        verify(bookService, times(1)).getBookByTitle(anyString());
    }

    @Test
    void deleteBook() throws Exception {
        when(bookService.deleteBook(anyInt())).thenReturn("Deleted");

        mockMvc.perform(delete("/book/1/delete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Deleted"));

        verify(bookService,times(1)).deleteBook(1);
    }

    @Test
    void updateBook() throws Exception {
        Book updatedBook =  Book.build("Updated title", "Updated author");
        BookRequest updtedBookRequest = updatedBook.toBookRequest();

        when(bookService.updateBook(anyInt(), any())).thenReturn(updtedBookRequest);

        mockMvc.perform(put("/book/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
                .andExpect(jsonPath("$.author").value(updatedBook.getAuthor()))
                .andExpect(jsonPath("$.available").value(updatedBook.isAvailable()));

        verify(bookService,times(1)).updateBook(anyInt(), any());
    }


    @Test
    void handleException() throws Exception {
        BookNotFoundException exception = new BookNotFoundException("Test book was not found.");
        when(bookService.getBookByTitle(anyString())).thenThrow(exception);

        mockMvc.perform(get("/book/findByTitle/testBook"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(exception.getMessage())));

        verify(bookService, times(1)).getBookByTitle(anyString());
    }
}