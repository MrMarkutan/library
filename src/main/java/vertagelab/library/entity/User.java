package vertagelab.library.entity;

import vertagelab.library.dto.UserRequest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String name;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> bookList = new ArrayList<>();

    public User() {
    }

//    public User(int userId, String name) {
//        this.userId = userId;
//        this.name = name;
//        bookList = new ArrayList<>();
//    }

    public User(String name, List<Book> bookList) {
        this.name = name;
        this.bookList = bookList;
    }

    public void addBook(Book book) {
        book.setUser(this);
        book.setAvailable(false);
        bookList.add(book);
    }

    public void removeBook(Book book) {
        book.setUser(null);
        book.setAvailable(true);
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public UserRequest toUserRequest() {
        return UserRequest.build(this.getName(),
                this.getBookList().stream()
                        .map(Book::toBookRequest)
                        .collect(Collectors.toList()));
    }
}
