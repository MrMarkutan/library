package vertagelab.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vertagelab.library.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByName(String name);
}
