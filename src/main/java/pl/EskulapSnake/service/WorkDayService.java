package pl.EskulapSnake.service;

import org.springframework.stereotype.Service;
import pl.EskulapSnake.repository.WorkDayRepository;

@Service
public class WorkDayService {
    private WorkDayRepository workDayRepository;
    public  WorkDayService(WorkDayRepository  workDayRepository) {
        this.workDayRepository = workDayRepository;
    }
}
