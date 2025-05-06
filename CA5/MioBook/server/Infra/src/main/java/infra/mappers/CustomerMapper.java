package infra.mappers;

import domain.entities.cart.Cart;
import domain.entities.user.Customer;
import domain.entities.user.Role;
import infra.daos.CustomerDao;
import infra.daos.WalletDao;
import infra.repositories.jpa.CartDaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper implements IMapper<Customer, CustomerDao> {

    private final AddressMapper addressMapper;
    private final CartDaoRepository cartDaoRepository;

    public Customer mapWithCart(CustomerDao dao, CartItemMapper cartItemMapper) {
        Customer customer = toDomain(dao);
        Cart cart = new Cart(customer);
        cartDaoRepository.findByCustomerId(dao.getId()).forEach(cartItem -> cart.addItem(cartItemMapper.toDomain(cartItem)));
        customer.setCart(cart);
        return customer;
    }

    @Override
    public Customer toDomain(CustomerDao dao) {
        return Customer.builder()
            .id(dao.getId())
            .username(dao.getName())
            .password(dao.getPassword())
            .email(dao.getEmail())
            .address(addressMapper.toValueObj(dao.getAddress()))
            .role(Role.CUSTOMER)
            .credit(dao.getWallet().getCredit())
            .build();
    }

    @Override
    public CustomerDao toDao(Customer entity) {
        CustomerDao dao = new CustomerDao();
        dao.setId(entity.getId());
        dao.setName(entity.getUsername());
        dao.setPassword(entity.getPassword());
        dao.setEmail(entity.getEmail());
        dao.setAddress(addressMapper.toDao(entity.getAddress()));
        WalletDao walletDao = new WalletDao();
        walletDao.setCustomer(dao);
        walletDao.setCustomerId(entity.getId());
        walletDao.setCredit(entity.getCredit());
        dao.setWallet(walletDao);
        return dao;
    }

    @Override
    public void update(Customer entity, CustomerDao dao) {
        dao.setId(entity.getId());
        dao.setName(entity.getUsername());
        dao.setPassword(entity.getPassword());
        dao.setEmail(entity.getEmail());
        dao.setAddress(addressMapper.toDao(entity.getAddress()));
        dao.getWallet().setCredit(entity.getCredit());
    }
}
