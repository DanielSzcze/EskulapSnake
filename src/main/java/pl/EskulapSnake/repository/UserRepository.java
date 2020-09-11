package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.EskulapSnake.model.Role;
import pl.EskulapSnake.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository   <User, Long> {

    User findUserByUsername(String userName);
    User findByEmail(String email);
}
