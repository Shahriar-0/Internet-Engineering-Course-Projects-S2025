package application.exceptions.dataaccessexceptions;

public class EntityDoesNotExist extends DataAccessException {
    public EntityDoesNotExist(Class<?> classType) {
        super("Entity " + classType.toString() + " does not exist!");
    }

    public EntityDoesNotExist() {
        super("Entity does not exist");
    }
}
