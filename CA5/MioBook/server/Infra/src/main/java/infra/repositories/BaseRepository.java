package infra.repositories;

import application.exceptions.dataaccessexceptions.EntityAlreadyExists;
import application.exceptions.dataaccessexceptions.EntityDoesNotExist;
import application.repositories.IBaseRepository;
import application.result.Result;
import domain.entities.DomainEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseRepository<T extends DomainEntity> implements IBaseRepository<T> {

	Map<Long, T> map = new HashMap<>();

	protected abstract Class<T> getEntityClassType();

	protected abstract T copyOf(T persistedEntity);

	@Override
	public Result<T> add(T entity) {
		if (map.containsKey(entity.getId()))
			return Result.failure(new EntityAlreadyExists(entity.getClass(), entity.getId()));

		map.put(entity.getId(), entity);
		return Result.success(copyOf(entity));
	}

	@Override
	public Result<T> remove(Long key) {
		T entity = map.remove(key);
		if (entity == null)
			return Result.failure(new EntityDoesNotExist(getEntityClassType(), key));

		return Result.success(copyOf(entity));
	}

	@Override
	public Result<T> update(T entity) {
		if (!map.containsKey(entity.getId()))
			return Result.failure(new EntityDoesNotExist(entity.getClass(), entity.getId()));

		map.put(entity.getId(), entity);
		return Result.success(copyOf(entity));
	}

	@Override
	public Result<T> find(Long key) {
		T entity = map.get(key);
		if (entity == null)
			return Result.failure(new EntityDoesNotExist(getEntityClassType(), key));

		return Result.success(copyOf(entity));
	}

	@Override
	public Boolean exists(Long key) {
		return map.containsKey(key);
	}

	@Override
	public Result<T> get(Long key) {
		T entity = map.get(key);
		if (entity == null)
			return Result.failure(new EntityDoesNotExist(getEntityClassType(), key));

		return Result.success(entity);
	}

	@Override
	public Result<List<T>> getAll() {
		return Result.success(map.values().stream().map(this::copyOf).toList());
	}
}
