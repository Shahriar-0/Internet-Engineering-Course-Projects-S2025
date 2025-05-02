package infra.daos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "customer")
@NoArgsConstructor
public class CustomerDao {

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

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private WalletDao wallet;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<BookLicenseDao> bookLicenses;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CartItemDao> cartItems;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<PurchasedCartDao> purchasedCarts;
}
