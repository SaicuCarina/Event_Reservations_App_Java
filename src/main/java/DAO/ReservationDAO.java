package DAO;

import MODELS.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private MyDBConnection dbConnection;

    public ReservationDAO() {
        dbConnection = MyDBConnection.getInstance();
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservationList = new ArrayList<>();
        String query = "SELECT * FROM reservations";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reservation reservation = new Reservation(rs.getInt("id"), rs.getInt("event_id"), rs.getInt("user_id"), rs.getInt("seatsReserved"), rs.getTimestamp("reservationDateTime").toLocalDateTime());
                reservationList.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservationList;
    }
}
