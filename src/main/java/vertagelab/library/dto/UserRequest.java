package vertagelab.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vertagelab.library.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class UserRequest {
    private String name;
    private List<BookRequest> bookList = new ArrayList<>();

    public User toUser() {
        return new User(this.getName(),
                this.getBookList().stream()
                .map(BookRequest::toBook)
                .collect(Collectors.toList()));
    }

}
