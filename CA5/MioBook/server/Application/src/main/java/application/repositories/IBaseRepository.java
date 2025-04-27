package application.repositories;

import java.util.List;
import application.result.Result;
import domain.entities.DomainEntity;

public interface IBaseRepository<T extends DomainEntity> {
	Result<T> add(T entity);
	Result<T> remove(Long key);
	Result<T> update(T entity);
	Result<T> find(Long key);
	Result<T> get(Long key);
	Result<List<T>> getAll();
	Boolean exists(Long key);
}
