package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.EskulapSnake.model.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

//    List<Role> findByEmployee(Long id);
}
