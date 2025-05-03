package infra.daos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "book")
@NoArgsConstructor
public class BookDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String title;

    @NotBlank
    private String publisher;


    @NotNull
    private Integer publishedYear;

    @NotNull
    private Long price;

    @NotBlank
    private String synopsis;

    @NotBlank
    private String content;

    private String imageLink;

    private String coverLink;

    @NotNull
    private LocalDateTime dateAdded;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorDao author;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminDao admin;

    @ManyToMany
    @JoinTable(
        name = "book_genre",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<GenreDao> genres;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ReviewDao> reviews;
}
