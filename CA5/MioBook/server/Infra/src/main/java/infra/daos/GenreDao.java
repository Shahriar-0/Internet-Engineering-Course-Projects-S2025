package infra.daos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "genre")
@NoArgsConstructor
public class GenreDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String genre;

    @ManyToMany(mappedBy = "genres")
    private List<BookDao> books;
}
