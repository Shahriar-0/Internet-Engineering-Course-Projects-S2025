package application.exceptions.dataaccessexceptions;

public class EntityAlreadyExists extends DataAccessException {

	public EntityAlreadyExists(Class<?> entityType, Object key) {
		super("Entity with type of " + entityType.getSimpleName() + " and key value of " + key + " already exists!");
	}
}
