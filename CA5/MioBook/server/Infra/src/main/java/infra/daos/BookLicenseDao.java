package infra.daos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "license")
@NoArgsConstructor
public class BookLicenseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long price;

    @NotNull
    private LocalDateTime purchaseDateTime;

    private Integer validityDays;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerDao customer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookDao book;
}
