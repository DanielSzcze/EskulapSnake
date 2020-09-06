package pl.EskulapSnake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.EskulapSnake.model.User;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private String firstName;
    private String lastName;
    private String pesel;

}
