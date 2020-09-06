package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.dto.EntryDto;
import pl.EskulapSnake.model.Entry;
import pl.EskulapSnake.service.EntryService;

import java.util.List;

@RestController
@RequestMapping("/entries")
public class EntryController {

    private EntryService entryService;

    @Autowired
    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }


    @GetMapping
    public List<Entry> getAll() {
        return entryService.findAll();
    }

    @GetMapping("/{id}")
    public Entry findByid(@PathVariable("id") String identification) {
        long id = Long.parseLong(identification);
        return entryService.findById(id);


    }

    @PostMapping
    public Entry post(@RequestBody EntryDto entryDto) {
        return entryService.createNew(entryDto);
    }

    @PutMapping("/{id}")
    public Entry put(@PathVariable("id") String identification, @RequestBody EntryDto entryDto) {
        long id = Long.parseLong(identification);
        return entryService.update(id, entryDto);
    }

    @DeleteMapping
    public void deleteAll() {
        entryService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String identification) {


        long id = Long.parseLong(identification);
        entryService.deleteById(id);
    }

}
