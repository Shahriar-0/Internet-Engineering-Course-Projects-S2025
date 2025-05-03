package infra.mappers;

import domain.entities.user.Admin;
import domain.entities.user.Role;
import infra.daos.AdminDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminMapper {

    private final AddressMapper addressMapper;

    public Admin toDomain(AdminDao dao) {
        return Admin.builder()
            .id(dao.getId())
            .username(dao.getName())
            .password(dao.getPassword())
            .email(dao.getEmail())
            .role(Role.ADMIN)
            .address(addressMapper.toValueObj(dao.getAddress()))
            .build();
    }
}
