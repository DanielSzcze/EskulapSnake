package pl.EskulapSnake.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

}
