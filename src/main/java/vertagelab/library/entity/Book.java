package vertagelab.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import vertagelab.library.dto.BookRequest;

import javax.persistence.*;


@Entity
@Table(name = "books")
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "build")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @NonNull
    private String title;
    @NonNull
    private String author;
    @NonNull
    private boolean available = true;

    public Book(@NonNull String title, @NonNull String author, @NonNull boolean available) {
        this.title = title;
        this.author = author;
        this.available = available;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public BookRequest toBookRequest() {
        return BookRequest.build(this.getTitle(), this.getAuthor(), this.isAvailable());
    }
}
