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
        for (int i = 0; i < 10; i++) {
            Entry entry = new Entry();
            entry.setRecommendations("bla");
            entry.setExamination("lol");
            entry.setLocalDateTime(LocalDateTime.of(2020, 9, 8, 11, 15));
            Entry entry1 = new Entry();
            entry1.setRecommendations("bla");
            entry1.setExamination("lol");
            entry1.setLocalDateTime(LocalDateTime.of(2020, 6, 8, 11, 15));
            Patient patient = new Patient();
            patient.setLastName("lool");
            patient.setFirstName("kuba");
            Patient patient1 = new Patient();
            patient1.setFirstName("loki");
            patient1.setLastName("ppp");
            patientRepository.save(patient);
            patientRepository.save(patient1);
            WorkDay workDay = new WorkDay();
            WorkDay workDay1 = new WorkDay();
            workDay.setFromWorkTime(LocalDateTime.of(2020, 9, 8, 10, 00));
            workDay1.setFromWorkTime(LocalDateTime.of(2020, 6, 8, 10, 00));
            workDay.setToWorkTime((LocalDateTime.of(2020, 9, 8, 18, 00)));
            workDay1.setToWorkTime((LocalDateTime.of(2020, 6, 13, 18, 00)));


            Employee employee = new Employee();
            employee.setFirstName("firstName" + i);
            employee.setLastName("lastName" + i);
            entry.setEmployee(employee);
            entry1.setEmployee(employee);

            workDay.setEmployee(employee);
            workDay1.setEmployee(employee);
            employeeRepository.save(employee);
            workDayRepository.save(workDay);
            entryRepository.save(entry);
            entryRepository.save(entry1);
        }
    }
}
