package MODELS;

import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private int event_id;
    private int user_id;
    private int seats_reserved;
    private String reservation_date;
    private String cancellation_date;

    public Reservation(int id, int event_id, int user_id, int seats_reserved, String reservation_date, String cancellation_date) {
        this.id = id;
        this.event_id = event_id;
        this.user_id = user_id;
        this.seats_reserved = seats_reserved;
        this.reservation_date = reservation_date;
        this.cancellation_date = cancellation_date;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return event_id;
    }

    public void setEventId(int eventId) {
        this.event_id = eventId;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public int getSeatsReserved() {
        return seats_reserved;
    }

    public void setSeatsReserved(int seatsReserved) {
        this.seats_reserved = seatsReserved;
    }

    public String getReservationDateTime() {
        return reservation_date;
    }

    public void setReservationDateTime(String reservationDate) {
        this.reservation_date = reservationDate;
    }

    @Override
    public String toString() {
        return "Reservation [id=" + id + ", eventId=" + event_id + ", userId=" + user_id + ", seatsReserved=" + seats_reserved + ", reservationDate=" + reservation_date + ", cancellationDate=" + cancellation_date +"]";
    }
}
