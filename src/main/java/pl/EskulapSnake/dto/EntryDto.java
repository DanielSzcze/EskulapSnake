package pl.EskulapSnake.dto;

import lombok.Data;

@Data
public class EntryDto {
    private  long patientId;
    private String examination;
    private String recommendations;

}
