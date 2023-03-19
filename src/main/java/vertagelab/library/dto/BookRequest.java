package vertagelab.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vertagelab.library.entity.Book;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class BookRequest {
    private String title;
    private String author;
    private boolean available = true;

    public Book toBook() {
        return new Book(this.getTitle(), this.getAuthor(), this.isAvailable());
    }
}
