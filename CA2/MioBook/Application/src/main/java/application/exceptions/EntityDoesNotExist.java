package application.exceptions;

public class EntityDoesNotExist extends BaseException {
    public EntityDoesNotExist(Class<?> classType) {
        super("Entity " + classType.toString() + " does not exist!");
    }

    public EntityDoesNotExist() {
        super("Entity does not exist");
    }
}
