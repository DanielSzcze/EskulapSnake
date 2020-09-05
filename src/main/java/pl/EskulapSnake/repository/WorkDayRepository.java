package pl.EskulapSnake.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.EskulapSnake.model.WorkDay;

public interface WorkDayRepository extends PagingAndSortingRepository<WorkDay, Long> {
}
