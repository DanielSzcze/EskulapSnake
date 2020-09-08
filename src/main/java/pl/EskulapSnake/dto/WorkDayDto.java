package pl.EskulapSnake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkDayDto {

    private Long employeeId;
    private LocalDateTime fromWorkTime;
    private LocalDateTime toWorkTime;

}
