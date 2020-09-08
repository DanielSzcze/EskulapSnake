package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.EskulapSnake.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String role_name);
}
