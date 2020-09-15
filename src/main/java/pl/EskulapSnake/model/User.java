package pl.EskulapSnake.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)

    private String password;

    @Column(nullable = false)
    private String email;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    private boolean enabled;
}
