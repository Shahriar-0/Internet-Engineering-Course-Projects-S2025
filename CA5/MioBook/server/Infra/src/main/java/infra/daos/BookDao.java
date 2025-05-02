package infra.daos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @Column(name = "published_year")
    private Integer publishedYear;

    @NotNull
    private Long price;

    @NotBlank
    private String synopsis;

    @NotBlank
    private String content;

    @Column(name = "image_link")
    private String imageLink;

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
}
