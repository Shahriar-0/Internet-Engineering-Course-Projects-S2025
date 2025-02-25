package application.repositories;

import application.result.Result;
import domain.entities.DomainEntity;

public interface IBaseRepository<KT, T extends DomainEntity<KT>> {
    Result<T> add(T entity);
    Result<T> remove(KT key); //TODO: Make better decision for this
    Result<T> update(T entity);
    Result<T> find(KT key);
}
