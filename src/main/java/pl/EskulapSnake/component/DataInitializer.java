package pl.EskulapSnake.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.EskulapSnake.model.Entry;
import pl.EskulapSnake.repository.EntryRepository;

/**
 * Tu możesz zainicializować dane do bazy by sprawdzić to w postmanie
 * Wstrzyknij swoje repo lub/i serwis i w run działaj do oporu
 */
@Component
public class DataInitializer implements ApplicationRunner {


    @Autowired
    EntryRepository entryRepository;

    @Override
    public void run(ApplicationArguments args) {
        for (int i =0; i<10; i++) {
            Entry entry = new Entry();
            entry.setRecommendations("bla");
            entry.setExamination("lol");

            entryRepository.save(entry);
        }
    }
}
