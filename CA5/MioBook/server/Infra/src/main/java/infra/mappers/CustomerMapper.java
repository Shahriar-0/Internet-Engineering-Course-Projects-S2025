package infra.mappers;

import domain.entities.user.Customer;
import domain.entities.user.Role;
import infra.daos.CustomerDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

    private final AddressMapper addressMapper;

    public Customer toSimpleDomain(CustomerDao dao) {
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
}
