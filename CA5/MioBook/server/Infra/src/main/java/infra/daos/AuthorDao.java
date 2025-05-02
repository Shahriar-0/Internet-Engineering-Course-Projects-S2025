package infra.daos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "author")
@NoArgsConstructor
public class AuthorDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    @Column(name = "pen_name")
    private String penName;

    @NotBlank
    private String nationality;

    @NotNull
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "death_date")
    private LocalDate deathDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminDao admin;

    //TODO: Add BookDao
}
