package pl.EskulapSnake.service;

import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.dto.WorkDayDto;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.WorkDay;
import pl.EskulapSnake.repository.EmployeeRepository;
import pl.EskulapSnake.repository.WorkDayRepository;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class WorkDayService {

    private WorkDayRepository workDayRepository;
    private EmployeeRepository employeeRepository;

    public  WorkDayService(WorkDayRepository  workDayRepository, EmployeeRepository employeeRepository) {
        this.workDayRepository = workDayRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<WorkDay> findAll() {
        return workDayRepository.findAll();
    }

    public WorkDay findById(Long id) {
        return workDayRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Work day not found!"));
    }

    public List<WorkDay> findByEmployee(Long employeeId){
        Optional<Employee> employeeToFind = employeeRepository.findById(employeeId);
        employeeToFind.orElseThrow(() -> new EntityNotFoundException("Employee not found!"));
        return workDayRepository.findByEmployee(employeeToFind.get());
    }

    @Transactional
    public WorkDay createNew(WorkDayDto workDayDto) {
        WorkDay workDay = new WorkDay();
        setFields(workDay, workDayDto);
        return workDayRepository.save(workDay);
    }

    @Transactional
    public WorkDay update (WorkDayDto workDayDto, Long id) {
        Optional<WorkDay> workDayToUpdate = workDayRepository.findById(id);
        workDayToUpdate.orElseThrow(() -> new EntityNotFoundException("Work day not found!"));
        WorkDay workDay = workDayToUpdate.get();
        setFields(workDay, workDayDto);
        return workDayRepository.save(workDay);
    }


    private WorkDay setFields (WorkDay workDay, WorkDayDto workDayDto){


        return workDay;
    }


}
