package pl.EskulapSnake.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.EskulapSnake.model.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
}
