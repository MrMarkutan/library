package vertagelab.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import vertagelab.library.entity.Book;
import vertagelab.library.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {


    @Mock
    BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    private static final String addBookToDB =
            "insert into book_table(book_id,title,author,available) values (11,'Test_title', 'Test_author', true)";

    @Autowired
    private JdbcTemplate jdbc;

    private Book book;

    @BeforeEach
    void setUp() {
//        jdbc.execute(addBookToDB);
        book = new Book("Test Title", "Test Author");
    }

    @Test
    void saveBook() {
        when(bookRepository.save(any())).thenReturn(book);

        Book savedBook = bookService.saveBook(new Book("Title to save", "Author to save"));

        assertNotNull(savedBook);
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertEquals(book.getAuthor(), savedBook.getAuthor());
        assertTrue(savedBook.isAvailable());

        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void saveBooks() {
        when(bookRepository.saveAll(any())).thenReturn(List.of(book, book));

        List<Book> savedBooks = bookService.saveBooks(List.of(
                new Book("Title to save 1", "Author to save 1"),
                new Book("Title to save 2", "Author to save 2")));

        assertNotNull(savedBooks);
        assertEquals(2, savedBooks.size());

        assertEquals(book.getTitle(), savedBooks.get(0).getTitle());
        assertEquals(book.getAuthor(), savedBooks.get(0).getAuthor());
        assertTrue(savedBooks.get(0).isAvailable());

        assertEquals(book.getTitle(), savedBooks.get(1).getTitle());
        assertEquals(book.getAuthor(), savedBooks.get(1).getAuthor());
        assertTrue(savedBooks.get(1).isAvailable());

        verify(bookRepository, times(1)).saveAll(any());
    }

    @Test
    void getBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book, book));

        List<Book> books = bookService.getBooks();
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals(book.getTitle(), books.get(0).getTitle());
        assertEquals(book.getAuthor(), books.get(0).getAuthor());
        assertTrue(books.get(0).isAvailable());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById() {
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));

        Book bookById = bookService.getBookById(100);
        assertNotNull(bookById);
        assertEquals("Test Title", bookById.getTitle());
        assertEquals("Test Author", bookById.getAuthor());
        assertTrue(bookById.isAvailable());

        verify(bookRepository, times(1)).findById(100);
    }

    @Test
    void getBookByTitle() {
        when(bookRepository.findByTitle(anyString())).thenReturn(Optional.of(book));

        Book bookByTitle = bookService.getBookByTitle(book.getTitle());
        assertNotNull(bookByTitle);
        assertEquals(book.getTitle(), bookByTitle.getTitle());
        assertEquals(book.getAuthor(), bookByTitle.getAuthor());
        assertTrue(bookByTitle.isAvailable());

        verify(bookRepository, times(1)).findByTitle(anyString());
    }

    @Test
    void deleteBook() {
/// TODO: 15.03.2023 resolve

        bookService.deleteBook(10);

        verify(bookRepository, times(1)).delete(any());
    }

    @Test
    void updateBook() {
        when(bookRepository.save(any())).thenReturn(book);
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));

        Book updatedBook = bookService.updateBook(100, new Book("New", "Book"));

        assertNotNull(updatedBook);
        assertEquals(book.getTitle(), updatedBook.getTitle());
        assertEquals(book.getAuthor(), updatedBook.getAuthor());
        assertTrue(updatedBook.isAvailable());

        verify(bookRepository, times(1)).save(any());

    }
}