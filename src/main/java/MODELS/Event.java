package MODELS;

import java.time.LocalDateTime;

public class Event {
    private int id;
    private String name;
    private LocalDateTime dateTime;
    private Location location;
    private int availableSeats;

    public Event(int id, String name, LocalDateTime dateTime, Location location, int availableSeats) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.location = location;
        this.availableSeats = availableSeats;
    }

    // getters and setters

    @Override
    public String toString() {
        return "Event [id=" + id + ", name=" + name + ", dateTime=" + dateTime + ", location=" + location + ", availableSeats=" + availableSeats + "]";
    }
}
