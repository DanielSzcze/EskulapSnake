package pl.EskulapSnake.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.EskulapSnake.model.User;

import java.util.Optional;

@Data
@NoArgsConstructor
public class EmployeeDto {

    private String firstName;
    private String lastName;
    private String pesel;

    public EmployeeDto(String firstName, String lastName, String pesel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

}
