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

    //TODO: Refactor duplications
    @Override
    public Result<T> add(T entity) {
        if (map.containsKey(entity.getKey()))
            return Result.failureOf(new EntityAlreadyExists(entity.getClass()));

        map.put(entity.getKey(), entity);
        return Result.successOf(entity);
    }

    @Override
    public Result<T> remove(KT key) {
        T result = map.remove(key);
        if (result == null)
            return Result.failureOf(new EntityDoesNotExist()); // TODO: somehow find class here

        return Result.successOf(result);
    }

    @Override
    public Result<T> update(T entity) {
        if (!map.containsKey(entity.getKey()))
            return Result.failureOf(new EntityDoesNotExist(entity.getClass()));

        map.put(entity.getKey(), entity);
        return Result.successOf(entity);
    }

    @Override
    public Result<T> find(KT key) {
        T result = map.get(key);
        if (result == null)
            return Result.failureOf(new EntityDoesNotExist()); // TODO: somehow find class here

        return Result.successOf(result);
    }

    @Override
    public boolean exists(KT key) {
        return map.containsKey(key);
    }
}
