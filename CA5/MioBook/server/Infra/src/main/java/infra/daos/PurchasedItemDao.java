package infra.daos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "purchased_item")
@NoArgsConstructor
public class PurchasedItemDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer borrowDays;

    @NotNull
    private boolean isBorrowed;

    @NotNull
    private Long price;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookDao book;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "purchase_history_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PurchasedCartDao purchasedCart;
}
