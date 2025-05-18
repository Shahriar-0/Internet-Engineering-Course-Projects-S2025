package infra.mappers;

import domain.entities.user.Admin;
import domain.entities.user.Role;
import infra.daos.AdminDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminMapper implements IMapper<Admin, AdminDao> {

    private final AddressMapper addressMapper;

    @Override
    public Admin toDomain(AdminDao dao) {
        return Admin.builder()
            .id(dao.getId())
            .username(dao.getName())
            .password(dao.getPassword())
            .email(dao.getEmail())
            .role(Role.ADMIN)
            .address(addressMapper.toValueObj(dao.getAddress()))
            .salt(dao.getSalt())
            .build();
    }

    @Override
    public AdminDao toDao(Admin entity) {
        AdminDao dao = new AdminDao();
        dao.setId(entity.getId());
        dao.setName(entity.getUsername());
        dao.setPassword(entity.getPassword());
        dao.setEmail(entity.getEmail());
        dao.setAddress(addressMapper.toDao(entity.getAddress()));
        dao.setSalt(entity.getSalt());
        return dao;
    }

    @Override
    public void update(Admin entity, AdminDao dao) {
        dao.setId(entity.getId());
        dao.setName(entity.getUsername());
        dao.setPassword(entity.getPassword());
        dao.setEmail(entity.getEmail());
        dao.setAddress(addressMapper.toDao(entity.getAddress()));
        dao.setSalt(entity.getSalt());
    }
}
