package pl.EskulapSnake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.EskulapSnake.model.Employee;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkDayDto {

    private Long employee_id;
    private LocalDateTime fromWorkTime;
    private LocalDateTime toWorkTime;

}
