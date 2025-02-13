public record Room(String id, int capacity) {
    int getCapacity() {
        return capacity;
    }

    String getId() {
        return id;
    }
}
