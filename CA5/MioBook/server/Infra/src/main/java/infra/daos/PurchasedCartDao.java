package infra.daos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "purchase_history")
@NoArgsConstructor
public class PurchasedCartDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime purchaseDateTime;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerDao customer;

    @OneToMany(mappedBy = "purchasedCart", cascade = CascadeType.REMOVE)
    private List<PurchasedItemDao> purchasedItems;
}
