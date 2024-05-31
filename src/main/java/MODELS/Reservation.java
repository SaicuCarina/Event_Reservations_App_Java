package MODELS;

import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private int eventId;
    private int userId;
    private int seatsReserved;
    private LocalDateTime reservationDateTime;

    public Reservation(int id, int eventId, int userId, int seatsReserved, LocalDateTime reservationDateTime) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.seatsReserved = seatsReserved;
        this.reservationDateTime = reservationDateTime;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSeatsReserved() {
        return seatsReserved;
    }

    public void setSeatsReserved(int seatsReserved) {
        this.seatsReserved = seatsReserved;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    @Override
    public String toString() {
        return "Reservation [id=" + id + ", eventId=" + eventId + ", userId=" + userId + ", seatsReserved=" + seatsReserved + ", reservationDateTime=" + reservationDateTime + "]";
    }
}
