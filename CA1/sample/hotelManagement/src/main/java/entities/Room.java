package entities;
public record Room(String id, int capacity) {
    public int getCapacity() {
        return capacity;
    }

    public String getId() {
        return id;
    }
}
