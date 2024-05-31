package MODELS;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event {
    private int id;
    private String name;
    private String date;
    private String time;
    private int location_id;
    private int available_seats;

    public Event(int id, String name, String date, String time, int location_id, int available_seats) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.location_id = location_id;
        this.available_seats = available_seats;
    }

    public String getDate() {
        return date;
    }

    public int getAvailableSeats() {
        return available_seats;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", name=" + name + ", date=" + date +", time=" + time + ", location=" + location_id + ", availableSeats=" + available_seats + "]";
    }

    public String getTime() {
        return time;
    }

    public int getLocationId() {
        return location_id;
    }

    public int getId() {
        return id;
    }

    public void setAvailableSeats(int i) {
        this.available_seats = i;
    }

    public String getName() {
        return name;
    }
}
