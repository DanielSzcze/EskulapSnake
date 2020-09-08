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
import pl.EskulapSnake.model.Entry;
import pl.EskulapSnake.model.Role;
import pl.EskulapSnake.model.User;
import pl.EskulapSnake.repository.EntryRepository;
import pl.EskulapSnake.repository.RoleRepository;
import pl.EskulapSnake.repository.UserRepository;

import java.util.List;

/**
 * Tu możesz zainicializować dane do bazy by sprawdzić to w postmanie
 * Wstrzyknij swoje repo lub/i serwis i w run działaj do oporu
 */
@Component
public class DataInitializer implements ApplicationRunner, ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EntryRepository entryRepository;
    @Autowired
    ApplicationContext applicationContext;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        for (int i = 0; i < 10; i++) {
            Entry entry = new Entry();
            entry.setRecommendations("bla");
            entry.setExamination("lol");

            entryRepository.save(entry);
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
