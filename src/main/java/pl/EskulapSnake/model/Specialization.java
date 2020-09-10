package pl.EskulapSnake.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "specializations")
@Data
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private  String name;

}
