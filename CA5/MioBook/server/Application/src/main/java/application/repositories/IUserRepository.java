package application.repositories;

import domain.entities.cart.CartItem;
import domain.entities.cart.PurchasedCart;
import domain.entities.user.Customer;
import domain.entities.user.User;

import java.util.Optional;

public interface IUserRepository extends IBaseRepository<User> {
    Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
	boolean existsByEmail(String email);
    CartItem saveCart(CartItem cartItem);
    Customer update(Customer customer, PurchasedCart purchasedCart);
}
