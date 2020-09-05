package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.EskulapSnake.model.WorkDay;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
}
