package infra.mappers;

public interface IMapper<T, K> {
    T toDomain(K dao);
    K toDao(T entity);
    void update(T entity, K dao);
}
