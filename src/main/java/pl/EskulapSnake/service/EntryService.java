package pl.EskulapSnake.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.dto.EntryDto;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.Entry;
import pl.EskulapSnake.model.Patient;
import pl.EskulapSnake.model.VisitType;
import pl.EskulapSnake.repository.EmployeeRepository;
import pl.EskulapSnake.repository.EntryRepository;
import pl.EskulapSnake.repository.PatientRepository;
import pl.EskulapSnake.repository.VisitTypeRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntryService {
    private EmployeeRepository employeeRepository;
    private EntryRepository entryRepository;
    private PatientRepository patientRepository;
    private VisitTypeRepository visitTypeRepository;

    public EntryService(EntryRepository entryRepository,
                        EmployeeRepository employeeRepository,
                        PatientRepository patientRepository,
                        VisitTypeRepository visitTypeRepository) {
        this.entryRepository = entryRepository;
        this.employeeRepository = employeeRepository;
        this.patientRepository = patientRepository;
        this.visitTypeRepository = visitTypeRepository;
    }

    public List<Entry> findAll() {
        List<Entry> entries = entryRepository.findAll();
        return entries;
    }

    public Entry findById(Long id) {
        return entryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("In DB is no Entry with this id"));
    }

        public List<Entry> findByEmployeeIdAndDate(Long employeeId, String monthAndYear){
            String[] dateInArray = monthAndYear.split("\\.");
            int month = Integer.parseInt(dateInArray[0]);
            int year = Integer.parseInt(dateInArray[1]);
            if(year<100) year+=2000;
            LocalDateTime beginOfMonth = this.getBeginOfMonth(month, year);
            LocalDateTime endOfMonth = this.getEndOfMonth(month, year);
            return entryRepository.getByEmpIdAndTimeInterval(employeeId, beginOfMonth, endOfMonth);
        }
    private LocalDateTime getBeginOfMonth(Integer month, Integer year) {
        LocalDateTime minDateOfMonth = LocalDateTime.of(year, month, 1, 00,00);
        return minDateOfMonth;

    }
    private LocalDateTime getEndOfMonth(Integer month, Integer year) {

        LocalDateTime maxDateOfMonth = LocalDateTime.of(year, month + 1, 1, 23, 59).minusDays(1);
        return maxDateOfMonth;
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
        Patient patient = patientRepository.findById(entryDto.getPatientId()).orElseThrow(() -> new EntityNotFoundException("In db is no patient with this id"));
        Employee employee = employeeRepository.findById(entryDto.getEmployeeId()).orElseThrow(() -> new EntityNotFoundException("In db is no employee with this id"));
        VisitType visitType = visitTypeRepository.findById(entryDto.getVisitTypeId()).orElseThrow(() -> new EntityNotFoundException("In db is no visit type with this id"));
        Entry entryToSave = getEntityFromDto(entryDto);
        entryToSave.setVisitType(visitType);
        patient.getEntries().add(entryToSave);
        entryToSave.setEmployee(employee);
        patientRepository.save(patient);
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
