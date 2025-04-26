package application.repositories;

import java.util.List;
import application.result.Result;
import domain.entities.DomainEntity;

public interface IBaseRepository<KT, T extends DomainEntity<KT>> {
	Result<T> add(T entity);
	Result<T> remove(KT key);
	Result<T> update(T entity);
	Result<T> find(KT key);
	Result<T> get(KT key);
	Result<List<T>> getAll();
	Boolean exists(KT key);
}
