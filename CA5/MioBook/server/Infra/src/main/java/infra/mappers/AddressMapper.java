package infra.mappers;

import domain.valueobjects.Address;
import infra.daos.AddressDao;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toValueObj(AddressDao dao) {
        return new Address(dao.getCountry(), dao.getCity());
    }
}
