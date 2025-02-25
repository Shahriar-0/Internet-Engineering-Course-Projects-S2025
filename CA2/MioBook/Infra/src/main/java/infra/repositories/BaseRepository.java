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

    @Override
    public Result<T> add(T entity) {
        if (map.containsKey(entity.getKey()))
            return Result.failureOf(new EntityAlreadyExists(entity.getClass(), entity.getKey()));

        map.put(entity.getKey(), entity);
        return Result.successOf(copyOf(entity));
    }

    @Override
    public Result<T> remove(KT key) {
        T entity = map.remove(key);
        if (entity == null)
            return Result.failureOf(new EntityDoesNotExist(getEntityClassType(), key));

        return Result.successOf(copyOf(entity));
    }

    @Override
    public Result<T> update(T entity) {
        if (!map.containsKey(entity.getKey()))
            return Result.failureOf(new EntityDoesNotExist(entity.getClass(), entity.getKey()));

        map.put(entity.getKey(), entity);
        return Result.successOf(copyOf(entity));
    }

    @Override
    public Result<T> find(KT key) {
        T entity = map.get(key);
        if (entity == null)
            return Result.failureOf(new EntityDoesNotExist(getEntityClassType(), key));

        return Result.successOf(copyOf(entity));
    }

    @Override
    public boolean exists(KT key) {
        return map.containsKey(key);
    }
}
