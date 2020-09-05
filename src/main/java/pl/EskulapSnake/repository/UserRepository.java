package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.EskulapSnake.model.User;

public interface UserRepository implements JpaRepository<User, Long> {
}
