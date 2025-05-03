package infra.daos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "admin")
@NoArgsConstructor
public class AdminDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    private String password;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressDao address;

    @OneToMany(mappedBy = "admin")
    private List<AuthorDao> addedAuthors;

    @OneToMany(mappedBy = "admin")
    private List<BookDao> addedBooks;
}
