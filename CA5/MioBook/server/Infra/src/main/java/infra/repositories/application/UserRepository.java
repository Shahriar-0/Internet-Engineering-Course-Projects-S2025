package infra.repositories.application;

import application.repositories.IUserRepository;
import domain.entities.user.Admin;
import domain.entities.user.Customer;
import domain.entities.user.User;
import infra.daos.AdminDao;
import infra.daos.CustomerDao;
import infra.mappers.AdminMapper;
import infra.mappers.CustomerMapper;
import infra.repositories.jpa.AdminDaoRepository;
import infra.repositories.jpa.CustomerDaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository extends BaseRepository<User> implements IUserRepository {

    private final CustomerDaoRepository customerDaoRepository;
    private final AdminDaoRepository adminDaoRepository;
    private final CustomerMapper customerMapper;
    private final AdminMapper adminMapper;

	@Override
	protected Class<User> getEntityClassType() {
		return User.class;
	}

	@Override
	protected User copyOf(User persistedEntity) {
		if (persistedEntity instanceof Customer)
			return Customer
				.builder()
				.username(persistedEntity.getUsername())
				.address(persistedEntity.getAddress())
				.password(persistedEntity.getPassword())
				.email(persistedEntity.getEmail())
				.role(persistedEntity.getRole())
				.credit(((Customer) persistedEntity).getCredit())
				.build();
		else if (persistedEntity instanceof Admin)
			return Admin
				.builder()
				.username(persistedEntity.getUsername())
				.address(persistedEntity.getAddress())
				.password(persistedEntity.getPassword())
				.email(persistedEntity.getEmail())
				.role(persistedEntity.getRole())
				.build();
		else
			throw new IllegalArgumentException("Invalid user type: " + persistedEntity);
	}

	@Override
	public Boolean doesEmailExist(String email) {
		return map.values().stream().anyMatch(user -> user.getEmail().equals(email));
	}

	@Override
	public Optional<User> findByEmail(String email) {
		List<User> userList = map.values().stream().filter(u -> u.getEmail().equals(email)).toList();
		if (userList.isEmpty())
			return Optional.empty();

		return Optional.of(userList.getFirst());
	}

    @Override
    public Boolean doesUsernameExist(String username) {
        for (User user : map.values()) {
            if (username.equals(user.getUsername()))
                return true;
        }

        return false;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<CustomerDao> optionalCustomerDao = customerDaoRepository.findByName(username);
        if (optionalCustomerDao.isPresent())
            return Optional.of(customerMapper.toSimpleDomain(optionalCustomerDao.get()));

        Optional<AdminDao> optionalAdminDao = adminDaoRepository.findByName(username);
        if (optionalAdminDao.isPresent())
            return Optional.of(adminMapper.toDomain(optionalAdminDao.get()));

        return Optional.empty();
    }
}
