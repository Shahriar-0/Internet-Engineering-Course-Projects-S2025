package infra.mappers;

import domain.valueobjects.Address;
import infra.daos.AddressDao;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toValueObj(AddressDao dao) {
        return new Address(dao.getCountry(), dao.getCity());
    }

    public AddressDao toDao(Address entity) {
        AddressDao dao = new AddressDao();
        dao.setCity(entity.city());
        dao.setCountry(entity.country());
        return dao;
    }
}
