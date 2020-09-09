package pl.EskulapSnake.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false )
    private String username;

    @Column( nullable = false )
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    private int enabled;

}
