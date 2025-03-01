package domain.valueobjects;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import domain.entities.Customer;
import lombok.Getter;

@Getter
public class PurchaseHistory {

    @JsonIgnore
    private final Customer customer;

    private final List<PurchasedCart> purchaseHistory;

    public PurchaseHistory(Customer customer) {
        this.customer = customer;
        this.purchaseHistory = new ArrayList<>();
    }

    public PurchasedCart addPurchasedCart(PurchasedCart purchasedCart) {
        this.purchaseHistory.add(purchasedCart);
        return purchasedCart;
    }

    @JsonProperty("username")
    public String getUsername() {
        return customer.getUsername();
    }
}
