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
    BookController bookController;
    @Autowired
    private MockMvc mockMvc;

    private Book book;

    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        book = new Book("Test Title", "Test Author");
        book.setAvailable(true);
    }

    @Test
    void addBook() throws Exception {
        when(bookService.saveBook(any())).thenReturn(book);

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
        when(bookService.saveBooks(any())).thenReturn(List.of(book, book, book));

        mockMvc.perform(post("/book/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(book, book, book))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void findAllBooks() throws Exception {
        when(bookService.getBooks()).thenReturn(List.of(book, book, book));

        mockMvc.perform(get("/book/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));

        verify(bookService, times(1)).getBooks();
    }

    @Test
    void testGetBookById() throws Exception {
        when(bookService.getBookById(anyInt())).thenReturn(book);

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
        when(bookService.getBookByTitle(anyString())).thenReturn(book);

        mockMvc.perform(get("/book/findByTitle/testTitle"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.available").value(book.isAvailable()));

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
        Book updatedBook = new Book("Updated title", "Updated author");

        when(bookService.updateBook(anyInt(), any())).thenReturn(updatedBook);

        mockMvc.perform(put("/book/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
                .andExpect(jsonPath("$.author").value(updatedBook.getAuthor()))
                .andExpect(jsonPath("$.available").value(updatedBook.isAvailable()));
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