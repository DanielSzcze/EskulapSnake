package pl.EskulapSnake.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.Entry;
import pl.EskulapSnake.model.Patient;
import pl.EskulapSnake.model.WorkDay;
import pl.EskulapSnake.repository.EmployeeRepository;
import pl.EskulapSnake.repository.EntryRepository;
import pl.EskulapSnake.repository.PatientRepository;
import pl.EskulapSnake.repository.WorkDayRepository;

import java.time.LocalDateTime;


/**
 * Tu możesz zainicializować dane do bazy by sprawdzić to w postmanie
 * Wstrzyknij swoje repo lub/i serwis i w run działaj do oporu
 */
@Component
public class DataInitializer implements ApplicationRunner {


    @Autowired
    EntryRepository entryRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    WorkDayRepository workDayRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void run(ApplicationArguments args) {
        for (int i =0; i<10; i++) {
            Entry entry = new Entry();
            entry.setRecommendations("bla");
            entry.setExamination("lol");
            entryRepository.save(entry);
            Patient patient = new Patient();
            patient.setLastName("lool");
            patient.setFirstName("kuba");
            patientRepository.save(patient);
            WorkDay workDay = new WorkDay();
            WorkDay workDay1 = new WorkDay();
            workDay.setFromWorkTime(LocalDateTime.of(2020,8,25,12,00));
            workDay1.setFromWorkTime(LocalDateTime.of(2020,6,25,12,00));
            workDay.setToWorkTime((LocalDateTime.of(2020,8,25,20,00)));
            workDay1.setToWorkTime((LocalDateTime.of(2020,6,25,20,00)));

            Employee employee = new Employee();
            employee.setFirstName("firstName" + i);
            employee.setLastName("lastName" + i);


            workDay.setEmployee(employee);
            workDay1.setEmployee(employee);
            employee.getWorkDays().add(workDay);
            employee.getWorkDays().add(workDay1);
            employeeRepository.save(employee);
            workDayRepository.save(workDay);

        }
    }
}
