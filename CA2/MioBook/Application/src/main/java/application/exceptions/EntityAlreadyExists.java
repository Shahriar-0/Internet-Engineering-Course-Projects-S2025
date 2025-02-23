package application.exceptions;

public class EntityAlreadyExists extends BaseException {
    public EntityAlreadyExists(Class<?> classType) {
        super("Entity " + classType.toString() + " already exists!");
    }
}
