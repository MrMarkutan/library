package vertagelab.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
Table(name = "books")
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "build")
public class Book {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int bookId;
    private String title;
    private String author;
    private boolean available = true;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Book() {
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
//        setAvailable(true);
    }

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
}
