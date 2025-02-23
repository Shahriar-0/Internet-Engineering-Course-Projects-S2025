package infra.repositories;

import application.exceptions.EntityAlreadyExists;
import application.exceptions.EntityDoesNotExist;
import application.repositories.IBaseRepository;
import application.response.Response;
import domain.entities.DomainEntity;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseRepository<KT, T extends DomainEntity<KT>> implements IBaseRepository<KT, T> {
    Map<KT, T> map = new HashMap<>();

    //TODO: Refactor duplications
    @Override
    public Response<T> add(T entity) {
        if (map.containsKey(entity.getKey()))
            return Response.failureOf(new EntityAlreadyExists(entity.getClass()));

        return Response.successOf(map.put(entity.getKey(), entity));
    }

    @Override
    public Response<T> remove(KT key) {
        T result = map.remove(key);
        if (result == null)
            return Response.failureOf(new EntityDoesNotExist()); // TODO: somehow find class here

        return Response.successOf(result);
    }

    @Override
    public Response<T> update(T entity) {
        if (!map.containsKey(entity.getKey()))
            return Response.failureOf(new EntityDoesNotExist(entity.getClass()));

        return Response.successOf(map.put(entity.getKey(), entity));
    }

    @Override
    public Response<T> find(KT key) {
        T result = map.get(key);
        if (result == null)
            return Response.failureOf(new EntityDoesNotExist()); // TODO: somehow find class here

        return Response.successOf(result);
    }
}
