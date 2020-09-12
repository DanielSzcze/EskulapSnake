package pl.EskulapSnake.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.dto.EntryDto;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.Entry;
import pl.EskulapSnake.model.Patient;
import pl.EskulapSnake.repository.EmployeeRepository;
import pl.EskulapSnake.repository.EntryRepository;
import pl.EskulapSnake.repository.PatientRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntryService {
    private EmployeeRepository employeeRepository;
    private EntryRepository entryRepository;
    private PatientRepository patientRepository;

    public EntryService(EntryRepository entryRepository,
                        EmployeeRepository employeeRepository,
                        PatientRepository patientRepository) {
        this.entryRepository = entryRepository;
        this.employeeRepository = employeeRepository;
        this.patientRepository = patientRepository;
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
    public Entry createNew(EntryDto entryDto, Employee author) {
        Patient owner = patientRepository.findById(entryDto.getPatientId()).orElseThrow(() -> new EntityNotFoundException("In db is no patient with this id"));
        Entry entryToSave = getEntityFromDto(entryDto);
        owner.getEntries().add(entryToSave);
        patientRepository.save(owner);
        entryToSave.setEmployee(author);
        entryRepository.save(entryToSave);
        return entryToSave;
    }

    @Transactional
    public Entry update(Long id, EntryDto entryDto) {
        Entry entryToUpdate = entryRepository.findById(id).orElseThrow(()
                -> new  EntityNotFoundException("there is no entity in db with this id"));
        entryToUpdate=setFields( entryToUpdate, entryDto);
        entryRepository.save(entryToUpdate);
        return entryToUpdate;
    }

    private Entry setFields(Entry entryToUpdate, EntryDto entryDto) {
        if(entryDto.getExamination()!=null)entryToUpdate.setExamination(entryDto.getExamination());
        if(entryDto.getRecommendations()!=null)entryToUpdate.setRecommendations(entryDto.getRecommendations());
        entryToUpdate.setLocalDateTime(LocalDateTime.now());
        return  entryToUpdate;
    }

    private Entry getEntityFromDto(EntryDto entryDto) {
        Entry entryToUpdate = new Entry();
        if (entryDto.getExamination()!=null)entryToUpdate.setExamination(entryDto.getExamination());
        if (entryDto.getRecommendations()!=null)entryToUpdate.setRecommendations(entryDto.getRecommendations());
        if (entryDto.getLocalDateTime()!=null)entryToUpdate.setLocalDateTime(entryDto.getLocalDateTime());
        return entryToUpdate;
    }

}
