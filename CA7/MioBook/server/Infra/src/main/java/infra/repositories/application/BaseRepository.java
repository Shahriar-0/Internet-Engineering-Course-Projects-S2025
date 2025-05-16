package infra.repositories.application;

import application.exceptions.dataaccessexceptions.EntityDoesNotExist;
import application.repositories.IBaseRepository;
import domain.entities.DomainEntity;
import infra.mappers.IMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T extends DomainEntity, K> implements IBaseRepository<T> {

    protected abstract IMapper<T, K> getMapper();
    protected abstract JpaRepository<K, Long> getDaoRepository();

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(Long id) {
        Optional<K> optionalDao = getDaoRepository().findById(id);
        if (optionalDao.isEmpty())
            return Optional.empty();

        T entity = getMapper().toDomain(optionalDao.get());
        return Optional.of(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        List<K> daoList = getDaoRepository().findAll();
        return daoList.stream().map(dao -> getMapper().toDomain(dao)).toList();
    }

    @Override
    @Transactional
    public T save(T entity) {
        K persistedDao = getDaoRepository().save(getMapper().toDao(entity));
        return getMapper().toDomain(persistedDao);
    }

    @Override
    @Transactional
    public T update(T entity) {
        Optional<K> optionalDao = getDaoRepository().findById(entity.getId());
        if (optionalDao.isEmpty())
            throw new EntityDoesNotExist(entity.getClass(), entity.getId());

        K daoEntity = optionalDao.get();
        getMapper().update(entity, daoEntity);
        K updatedDao = getDaoRepository().save(daoEntity);
        return getMapper().toDomain(updatedDao);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return getDaoRepository().existsById(id);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        getDaoRepository().delete(getMapper().toDao(entity));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        getDaoRepository().deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        getDaoRepository().deleteAll();
    }

    @Override
    @Transactional
    public void flush() {
        getDaoRepository().flush();
    }
}
