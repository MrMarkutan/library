package vertagelab.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vertagelab.library.dto.BookRequest;
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

    private Book book;
    private BookRequest bookRequest;

    @BeforeEach
    void setUp() {
        book = Book.build("Test Title", "Test Author");
        bookRequest = book.toBookRequest();
    }

    @Test
    void saveBook() {
        when(bookRepository.save(any())).thenReturn(book);

        BookRequest savedBook = bookService.saveBook(bookRequest);

        assertNotNull(savedBook);
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertEquals(book.getAuthor(), savedBook.getAuthor());
        assertTrue(savedBook.isAvailable());

        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void saveBookWhichIsNotAvailable() {
        Book naBook = Book.build("Book is n/a", "I AM");
        naBook.setAvailable(false);

        BookRequest naBookRequest = naBook.toBookRequest();

        when(bookRepository.save(any())).thenReturn(naBook);

        BookRequest savedBook = bookService.saveBook(naBookRequest);

        assertNotNull(savedBook);
        assertEquals(naBook.getTitle(), savedBook.getTitle());
        assertEquals(naBook.getAuthor(), savedBook.getAuthor());
        assertFalse(savedBook.isAvailable());

        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void saveBooks() {
        when(bookRepository.saveAll(any())).thenReturn(List.of(book, book));

        List<BookRequest> savedBooks = bookService.saveBooks(List.of(
                book.toBookRequest(), book.toBookRequest()));

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

        List<BookRequest> books = bookService.getBooks();
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

        BookRequest bookById = bookService.getBookById(100);
        assertNotNull(bookById);
        assertEquals(book.getTitle(), bookById.getTitle());
        assertEquals(book.getAuthor(), bookById.getAuthor());
        assertTrue(bookById.isAvailable());

        verify(bookRepository, times(1)).findById(100);
    }

    @Test
    void getBookByTitle() {
        when(bookRepository.findByTitle(anyString())).thenReturn(Optional.of(book));

        BookRequest bookByTitle = bookService.getBookByTitle(book.getTitle());
        assertNotNull(bookByTitle);
        assertEquals(book.getTitle(), bookByTitle.getTitle());
        assertEquals(book.getAuthor(), bookByTitle.getAuthor());
        assertTrue(bookByTitle.isAvailable());

        verify(bookRepository, times(1)).findByTitle(anyString());
    }

    @Test
    void deleteBook() {
        int bookId = 100;
        String result = bookService.deleteBook(bookId);

        assertEquals("Book #" + bookId + " was deleted.", result);

        verify(bookRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void updateBook() {
        Book extra = Book.build("New", "Book");
        BookRequest extraBookRequest = book.toBookRequest();

        when(bookRepository.save(any())).thenReturn(book);
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(extra));

        BookRequest updatedBookRequest = bookService.updateBook(100, extraBookRequest);

        assertNotNull(updatedBookRequest);
        assertEquals(book.getTitle(), updatedBookRequest.getTitle());
        assertEquals(book.getAuthor(), updatedBookRequest.getAuthor());
        assertTrue(updatedBookRequest.isAvailable());

        verify(bookRepository, times(1)).save(any());
    }
}