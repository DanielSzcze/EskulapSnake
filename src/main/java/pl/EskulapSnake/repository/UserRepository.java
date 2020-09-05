package pl.EskulapSnake.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.EskulapSnake.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}
