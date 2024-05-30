package MODELS;

public class Location {
    private int id;
    private String name;
    private String address;
    private int capacity;

    public Location(int id, String name, String address, int capacity) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    // getters and setters

    @Override
    public String toString() {
        return "Location [id=" + id + ", name=" + name + ", address=" + address + ", capacity=" + capacity + "]";
    }
}
