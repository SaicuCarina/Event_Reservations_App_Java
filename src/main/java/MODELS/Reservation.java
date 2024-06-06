package MODELS;

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
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Reservation [id=" + id + ", eventId=" + event_id + ", userId=" + user_id + ", seatsReserved=" + seats_reserved + ", reservationDate=" + reservation_date + ", cancellationDate=" + cancellation_date +"]";
    }
    public CharSequence getCancellationDate() {
        return cancellation_date;
    }
}
