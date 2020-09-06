package pl.EskulapSnake.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.dto.EntryDto;
import pl.EskulapSnake.model.Entry;
import pl.EskulapSnake.repository.EntryRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntryService {
    private EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public List<Entry> findAll() {
        List<Entry> entries = entryRepository.findAll();
        return entries;
    }

    public Entry findById(Long id) {
        return entryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("In DB is no Entry with this id"));
    }

    @Transactional
    public void deleteAll() {
        entryRepository.deleteAll();
    }

    @Transactional
    public void deleteById(Long id) {

        entryRepository.deleteById(id);
    }

    @Transactional
    public Entry createNew(EntryDto entryDto) {
        Entry entryToSave = getEntityFromDto(entryDto);
        entryRepository.save(entryToSave);
        return entryToSave;
    }
@Transactional
    public Entry update(Long id, EntryDto entryDto) {
        Entry entryToUpdate = getEntityFromDto(entryDto);
        entryToUpdate.setId(id);
        entryRepository.save(entryToUpdate);
        return entryToUpdate;
    }

    private Entry getEntityFromDto(EntryDto entryDto) {
        Entry entryToUpdate = new Entry();
        entryToUpdate.setExamination(entryDto.getExamination());
        entryToUpdate.setRecommendations(entryDto.getRecommendations());
        entryToUpdate.setLocalDateTime(LocalDateTime.now());
        return entryToUpdate;
    }


}
