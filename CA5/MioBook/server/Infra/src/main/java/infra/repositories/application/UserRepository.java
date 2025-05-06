package infra.repositories.application;

import application.exceptions.dataaccessexceptions.EntityDoesNotExist;
import application.repositories.IUserRepository;
import domain.entities.book.Book;
import domain.entities.cart.CartItem;
import domain.entities.cart.PurchasedCart;
import domain.entities.user.Admin;
import domain.entities.user.Customer;
import domain.entities.user.User;
import infra.daos.*;
import infra.daos.CartItemDao;
import infra.mappers.*;
import infra.repositories.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {

    private final static String USER_ROLE_NEEDED = "Id is not enough for this operation, user role needed!";
    private final static String UNKNOWN_USER_TYPE = "Unknown user type";

    private final CustomerDaoRepository customerDaoRepository;
    private final CartDaoRepository cartDaoRepository;
    private final AdminDaoRepository adminDaoRepository;
    private final PurchasedCartDaoRepository purchasedCartDaoRepository;
    private final BookLicenseDaoRepository bookLicenseDaoRepository;

    private final CartItemMapper cartItemMapper;
    private final CustomerMapper customerMapper;
    private final AdminMapper adminMapper;
    private final BookLicenseMapper bookLicenseMapper;
    private final BookMapper bookMapper;

    @Override
    public Optional<User> findById(Long id) {
        throw new UnsupportedOperationException(USER_ROLE_NEEDED);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        userList.addAll(customerDaoRepository.findAll().stream().map(customerMapper::toDomain).toList());
        userList.addAll(adminDaoRepository.findAll().stream().map(adminMapper::toDomain).toList());
        return userList;
    }

    @Override
    @Transactional
    public User save(User entity) {
        if (entity instanceof Customer customer)
            return saveCustomer(customer);
        else if (entity instanceof Admin admin)
            return saveAdmin(admin);
        else
            throw new IllegalArgumentException(UNKNOWN_USER_TYPE);
    }

    @Override
    @Transactional
    public User update(User entity) {
        if (entity instanceof Customer customer)
            return updateCustomer(customer);
        else if (entity instanceof Admin admin)
            return updateAdmin(admin);
        else
            throw new IllegalArgumentException(UNKNOWN_USER_TYPE);
    }

    @Override
    public boolean existsById(Long id) {
        throw new UnsupportedOperationException(USER_ROLE_NEEDED);
    }

    @Override
    @Transactional
    public void delete(User entity) {
        if (entity instanceof Customer)
            customerDaoRepository.deleteById(entity.getId());
        else if (entity instanceof Admin)
            adminDaoRepository.deleteById(entity.getId());
        else
            throw new IllegalArgumentException(UNKNOWN_USER_TYPE);
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException(USER_ROLE_NEEDED);
    }

    @Override
    @Transactional
    public void deleteAll() {
        customerDaoRepository.deleteAll();
        adminDaoRepository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        Optional<CustomerDao> optionalCustomerDao = customerDaoRepository.findByName(username);
        if (optionalCustomerDao.isPresent())
            return Optional.of(customerMapper.mapWithCartAndLicenses(optionalCustomerDao.get(), cartItemMapper, bookLicenseMapper));

        Optional<AdminDao> optionalAdminDao = adminDaoRepository.findByName(username);
        return optionalAdminDao.map(adminMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        Optional<CustomerDao> optionalCustomerDao = customerDaoRepository.findByEmail(email);
        if (optionalCustomerDao.isPresent())
            return Optional.of(customerMapper.mapWithCartAndLicenses(optionalCustomerDao.get(), cartItemMapper, bookLicenseMapper));

        Optional<AdminDao> optionalAdminDao = adminDaoRepository.findByEmail(email);
        return optionalAdminDao.map(adminMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return customerDaoRepository.existsByName(username) ||
            adminDaoRepository.existsByName(username);
    }

	@Override
    @Transactional(readOnly = true)
	public boolean existsByEmail(String email) {
        return customerDaoRepository.existsByEmail(email) ||
            adminDaoRepository.existsByEmail(email);
	}

    private Customer saveCustomer(Customer customer) {
        CustomerDao persistedDao = customerDaoRepository.save(customerMapper.toDao(customer));
        return customerMapper.toDomain(persistedDao);
    }

    private Admin saveAdmin(Admin admin) {
        AdminDao persistedDao = adminDaoRepository.save(adminMapper.toDao(admin));
        return adminMapper.toDomain(persistedDao);
    }

    private Customer updateCustomer(Customer customer) {
        Optional<CustomerDao> optionalDao = customerDaoRepository.findById(customer.getId());
        if (optionalDao.isEmpty())
            throw new EntityDoesNotExist(Customer.class, customer.getId());

        CustomerDao dao = optionalDao.get();
        customerMapper.update(customer, dao);
        dao = customerDaoRepository.save(dao);
        return customerMapper.toDomain(dao);
    }

    @Override
    @Transactional
    public Customer purchase(Customer customer, PurchasedCart purchasedCart) {
        Optional<CustomerDao> optionalDao = customerDaoRepository.findById(customer.getId());
        if (optionalDao.isEmpty())
            throw new EntityDoesNotExist(Customer.class, customer.getId());

        CustomerDao customerDao = optionalDao.get();

        cartDaoRepository.deleteByCustomerId(customer.getId());

        PurchasedCartDao purchasedCartDao = new PurchasedCartDao();
        purchasedCartDao.setPurchaseDateTime(purchasedCart.getPurchaseDateTime());
        purchasedCartDao.setCustomer(customerDao);
        List<PurchasedItemDao> purchasedItemDaoList = new ArrayList<>();
        purchasedCart.getItems().forEach(item -> {
            PurchasedItemDao itemDao = new PurchasedItemDao();
            itemDao.setBorrowed(item.isBorrowed());
            itemDao.setBorrowDays(item.getBorrowDays());
            itemDao.setPrice(item.getPrice());
            itemDao.setBook(bookMapper.toDao(item.getBook()));
            itemDao.setPurchasedCart(purchasedCartDao);
            purchasedItemDaoList.add(itemDao);
        });
        purchasedCartDao.setPurchasedItems(purchasedItemDaoList);
        purchasedCartDaoRepository.save(purchasedCartDao);

        List<BookLicenseDao> newLicenses = new ArrayList<>();
        customer.getPurchasedLicenses().forEach(license -> {
            if (license.getId() == null) {
                BookLicenseDao licenseDao = bookLicenseMapper.toDao(license);
                licenseDao.setCustomer(customerDao);
                licenseDao.setBook(bookMapper.toDao(license.getBook()));
                newLicenses.add(licenseDao);
            }
        });
        bookLicenseDaoRepository.saveAll(newLicenses);

        customerDao.getWallet().setCredit(customer.getCredit());
        customerDaoRepository.save(customerDao);

        return customer;
    }

    @Override
    @Transactional
    public void removeCart(Customer customer, Book book) {
        Optional<CustomerDao> optionalDao = customerDaoRepository.findById(customer.getId());
        if (optionalDao.isEmpty())
            throw new EntityDoesNotExist(Customer.class, customer.getId());

        Optional<CartItemDao> optionalCartItemDao = cartDaoRepository.findByCustomerIdAndBookId(customer.getId(), book.getId());
        if (optionalCartItemDao.isEmpty())
            throw new EntityDoesNotExist(CartItem.class, book.getId());

        cartDaoRepository.deleteByCustomerIdAndBookId(customer.getId(), book.getId());
    }

    private Admin updateAdmin(Admin admin) {
        Optional<AdminDao> optionalDao = adminDaoRepository.findById(admin.getId());
        if (optionalDao.isEmpty())
            throw new EntityDoesNotExist(Admin.class, admin.getId());

        AdminDao dao = optionalDao.get();
        adminMapper.update(admin, dao);
        dao = adminDaoRepository.save(dao);
        return adminMapper.toDomain(dao);
    }

    @Override
    @Transactional
    public CartItem saveCart(CartItem cartItem) {
        CartItemDao dao = cartDaoRepository.save(cartItemMapper.toDao(cartItem));
        return cartItemMapper.toDomain(dao);
    }
}
