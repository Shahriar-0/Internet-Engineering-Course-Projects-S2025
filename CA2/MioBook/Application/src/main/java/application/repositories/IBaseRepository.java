package application.repositories;

import application.response.Response;
import domain.entities.DomainEntity;

public interface IBaseRepository<KT, T extends DomainEntity<KT>> {
    Response<T> add(T entity);
    Response<T> remove(KT key); //TODO: Make better decision for this
    Response<T> update(T entity);
    Response<T> find(KT key);
}
