package vertagelab.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vertagelab.library.entity.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Integer> {
    Optional<Book> findByTitle(String title);
}
