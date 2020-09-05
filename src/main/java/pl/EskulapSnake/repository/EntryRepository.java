package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.EskulapSnake.model.Entry;

public interface EntryRepository  extends JpaRepository<Entry, Long> {
}
