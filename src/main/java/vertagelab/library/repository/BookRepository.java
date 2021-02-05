package vertagelab.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vertagelab.library.entity.Book;

public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findByTitle(String title);
}
