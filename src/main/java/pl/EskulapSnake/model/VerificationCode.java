package pl.EskulapSnake.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VerificationCode {
    @Id
    @GeneratedValue
    private Long id;
    private String code;
    @OneToOne
    private User user;
}
