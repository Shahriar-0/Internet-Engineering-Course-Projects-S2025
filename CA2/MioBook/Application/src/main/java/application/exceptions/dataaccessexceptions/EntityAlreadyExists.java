package application.exceptions.dataaccessexceptions;

public class EntityAlreadyExists extends DataAccessException {
    public EntityAlreadyExists(Class<?> classType) {
        super("Entity " + classType.toString() + " already exists!");
    }
}
