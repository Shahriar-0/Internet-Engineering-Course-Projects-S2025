package entities;
public record Room(String id, int capacity) {
    int getCapacity() {
        return capacity;
    }

    public String getId() {
        return id;
    }
}
