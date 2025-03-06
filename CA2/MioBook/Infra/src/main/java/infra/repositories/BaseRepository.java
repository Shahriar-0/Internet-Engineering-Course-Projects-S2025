package infra.repositories;

import application.exceptions.dataaccessexceptions.EntityAlreadyExists;
import application.exceptions.dataaccessexceptions.EntityDoesNotExist;
import application.repositories.IBaseRepository;
import application.result.Result;
import domain.entities.DomainEntity;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseRepository<KT, T extends DomainEntity<KT>> implements IBaseRepository<KT, T> {

	Map<KT, T> map = new HashMap<>();

	protected abstract Class<T> getEntityClassType();

	protected abstract T copyOf(T persistedEntity);

	/**
	 * Adds a new entity to the repository.
	 *
	 * @param entity The new entity to add.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.dataaccessexceptions.DataAccessException}.
	 */
	@Override
	public Result<T> add(T entity) {
		if (map.containsKey(entity.getKey()))
			return Result.failure(new EntityAlreadyExists(entity.getClass(), entity.getKey()));

		map.put(entity.getKey(), entity);
		return Result.success(copyOf(entity));
	}

	/**
	 * Removes an entity from the repository.
	 *
	 * @param key The key of the entity to remove.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.dataaccessexceptions.DataAccessException}.
	 */
	@Override
	public Result<T> remove(KT key) {
		T entity = map.remove(key);
		if (entity == null)
			return Result.failure(new EntityDoesNotExist(getEntityClassType(), key));

		return Result.success(copyOf(entity));
	}

	/**
	 * Updates an existing entity in the repository.
	 *
	 * @param entity The updated entity.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.dataaccessexceptions.DataAccessException}.
	 */
	@Override
	public Result<T> update(T entity) {
		if (!map.containsKey(entity.getKey()))
			return Result.failure(new EntityDoesNotExist(entity.getClass(), entity.getKey()));

		map.put(entity.getKey(), entity);
		return Result.success(copyOf(entity));
	}

	/**
	 * Finds an entity in the repository by its key, and returns a copy of it.
	 *
	 * @param key The key of the entity to find.
	 * @return A Result containing a copy of the found entity, or a failure with an
	 *         {@link application.exceptions.dataaccessexceptions.EntityDoesNotExist} exception if the
	 *         entity does not exist.
	 */
	@Override
	public Result<T> find(KT key) {
		T entity = map.get(key);
		if (entity == null)
			return Result.failure(new EntityDoesNotExist(getEntityClassType(), key));

		return Result.success(copyOf(entity));
	}

	/**
	 * Checks if an entity exists in the repository by its key.
	 *
	 * @param key The key of the entity to check.
	 * @return <code>true</code> if the entity exists, <code>false</code> otherwise.
	 */
	@Override
	public Boolean exists(KT key) {
		return map.containsKey(key);
	}

	/**
	 * Gets an entity from the repository by its key.
	 *
	 * @param key The key of the entity to get.
	 * @return A Result containing the found entity, or a failure with an
	 *         {@link application.exceptions.dataaccessexceptions.EntityDoesNotExist} exception if the
	 *         entity does not exist.
	 */
	@Override
	public Result<T> get(KT key) {
		T entity = map.get(key);
		if (entity == null)
			return Result.failure(new EntityDoesNotExist(getEntityClassType(), key));

		return Result.success(entity);
	}
}
