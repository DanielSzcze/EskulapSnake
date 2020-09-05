package pl.EskulapSnake.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.EskulapSnake.model.Entry;

public interface EntryRepository  extends PagingAndSortingRepository<Entry, Long> {
}
