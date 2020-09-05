package pl.EskulapSnake.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.EskulapSnake.model.Specialization;

public interface SpecializationRepository extends PagingAndSortingRepository<Specialization, Long> {
}
