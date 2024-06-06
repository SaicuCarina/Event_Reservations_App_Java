package MODELS;

import java.util.List;

public interface Reserve {
    boolean check(User user);
    void generateCancellationReport(int userId);
    void reserveEvent(User user, Event event, int seatsReserved);
    List<Reservation> getUserReservations(User user);
    void cancelReservation(int reservationId);
    String getReservationInfo(int reservationId);
}
