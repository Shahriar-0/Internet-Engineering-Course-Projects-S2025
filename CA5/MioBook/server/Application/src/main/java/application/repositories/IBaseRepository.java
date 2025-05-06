package application.repositories;

import domain.entities.DomainEntity;

import java.util.List;
import java.util.Optional;

public interface IBaseRepository<T extends DomainEntity> {
    Optional<T> findById(Long id);
    List<T> findAll();
    T save(T entity);
    T update(T entity);
    boolean existsById(Long id);
    void delete(T entity);
    void deleteById(Long id);
    void deleteAll();
    void flush();
}
