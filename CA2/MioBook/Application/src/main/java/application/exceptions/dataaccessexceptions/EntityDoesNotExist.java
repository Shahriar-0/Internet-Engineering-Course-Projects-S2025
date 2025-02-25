package application.exceptions.dataaccessexceptions;

public class EntityDoesNotExist extends DataAccessException {

	public EntityDoesNotExist(Class<?> entityType, Object key) {
		super("Entity with type of " + entityType.getSimpleName() + " and key value of " + key + " does not exist!");
	}
}
