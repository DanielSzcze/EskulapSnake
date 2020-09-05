package pl.EskulapSnake.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.EskulapSnake.model.VisitType;

public interface VisitTypeRepository extends PagingAndSortingRepository<VisitType, Long> {
}
