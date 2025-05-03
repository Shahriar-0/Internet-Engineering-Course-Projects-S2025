package infra.daos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
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
    private String penName;

    @NotBlank
    private String nationality;

    @NotNull
    private LocalDate birthDate;

    private LocalDate deathDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminDao admin;

    @OneToMany(mappedBy = "author")
    private List<BookDao> booksWritten;
}
