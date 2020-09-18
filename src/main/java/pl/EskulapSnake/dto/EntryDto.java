package pl.EskulapSnake.dto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EntryDto {

    private long patientId;
    private long employeeId;
    private LocalDateTime localDateTime;
    private long visitTypeId;
    private String examination;
    private String recommendations;
    private  String userNameOfPatient;
    private  String userNameOfEmployee;


}
