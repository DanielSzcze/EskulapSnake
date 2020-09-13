package pl.EskulapSnake.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.model.*;
import pl.EskulapSnake.repository.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Tu możesz zainicializować dane do bazy by sprawdzić to w postmanie
 * Wstrzyknij swoje repo lub/i serwis i w run działaj do oporu
 */
@Component
public class DataInitializer implements ApplicationRunner, ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    EntryRepository entryRepository;
    ApplicationContext applicationContext;
    WorkDayRepository workDayRepository;
    EmployeeRepository employeeRepository;
    PatientRepository patientRepository;
    VisitTypeRepository visitTypeRepository;

    @Autowired
    public DataInitializer(
            VisitTypeRepository visitTypeRepository,
            PatientRepository patientRepository,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            EntryRepository entryRepository,
            ApplicationContext applicationContext,
            WorkDayRepository workDayRepository,
            EmployeeRepository employeeRepository) {
        this.visitTypeRepository=visitTypeRepository;
        this.patientRepository=patientRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.entryRepository = entryRepository;
        this.applicationContext = applicationContext;
        this.workDayRepository = workDayRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
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

            WorkDay workDay = new WorkDay();
            WorkDay workDay1 = new WorkDay();
            workDay.setFromWorkTime(LocalDateTime.of(2020, 9, 8, 10, 00));
            workDay1.setFromWorkTime(LocalDateTime.of(2020, 6, 8, 8, 00));
            workDay.setToWorkTime((LocalDateTime.of(2020, 9, 8, 18, 00)));
            workDay1.setToWorkTime((LocalDateTime.of(2020, 6, 8, 16, 00)));


            Employee employee = new Employee();
            employee.setFirstName("firstName" + i);
            employee.setLastName("lastName" + i);
            entry.setEmployee(employee);
            entry1.setEmployee(employee);

            workDay.setEmployee(employee);
            workDay1.setEmployee(employee);
            employeeRepository.save(employee);
            workDayRepository.save(workDay);
            workDayRepository.save(workDay1);
            entryRepository.save(entry);
            entryRepository.save(entry1);
            patient.getEntries().add(entry);
            patient1.getEntries().add(entry1);
            patientRepository.save(patient);
            patientRepository.save(patient1);
            Role role = new Role();
            role.setName("role "+ i);
            roleRepository.save(role);
            VisitType visitType = new VisitType();
            visitType.setName("visitType "+i);
            visitTypeRepository.save(visitType);
        }
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_MANAGER");
        createRoleIfNotFound("ROLE_PHYSICIAN");
        createRoleIfNotFound("ROLE_RECORDER");
        createRoleIfNotFound("ROLE_PATIENT");
        createRoleIfNotFound("ROLE_EMPLOYEE");
        List<Role> adminRoles = roleRepository.findAll();
        User user = new User();
        user.setUsername(applicationContext.getEnvironment().getProperty("admin.username"));
        user.setPassword(passwordEncoder.encode(applicationContext.getEnvironment().getProperty("admin.passwd")));
        user.setEmail("eskulapsnake@gmail.com");
        user.setRoles(adminRoles);
        user.setEnabled(true);
        userRepository.save(user);
        alreadySetup = true;
    }

    private Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }
}
