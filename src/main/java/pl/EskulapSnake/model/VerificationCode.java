package pl.EskulapSnake.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "verification_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String code;

    @OneToOne
    private User user;
}
