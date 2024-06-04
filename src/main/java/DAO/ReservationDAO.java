package DAO;

import MODELS.EventCategory;
import MODELS.Location;
import MODELS.Reservation;
import MODELS.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationDAO {
    private MyDBConnection dbConnection;

    public ReservationDAO() {
        dbConnection = MyDBConnection.getInstance();
    }

    public List<Reservation> getAllReservations() {
        Connection connection = dbConnection.getConnection();
        List<Reservation> reservationList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM reservations");
            while (rs.next()) {
                int id = rs.getInt("id");
                int event_id = rs.getInt("event_id");
                int user_id = rs.getInt("user_id");
                int seats_reserved = rs.getInt("seats_reserved");
                String reservation_date = rs.getString("reservation_date");
                String cancellation_date = rs.getString("cancellation_date");

                Reservation reservation = new Reservation(id, event_id, user_id, seats_reserved, reservation_date, cancellation_date);
                reservationList.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservationList;
    }

    public void reserveEvent(int userId, int eventId, int seatsReserved) {


        Connection connection = dbConnection.getConnection();
        try {
            // Prepare the INSERT statement
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO reservations (user_id, event_id, seats_reserved, reservation_date) VALUES (?, ?, ?, ?)"
            );

            // Set the values for the placeholders
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, eventId);
            preparedStatement.setInt(3, seatsReserved);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDateTime = now.format(formatter);

            preparedStatement.setString(4, formattedDateTime);

            // Execute the INSERT statement
            preparedStatement.executeUpdate();

            //System.out.println("Reservation added to the database.");
        } catch (SQLException e) {
            System.err.println("Error adding reservation to the database: " + e.getMessage());
        }
    }
    public List<Reservation> getCancelledReservations(int userId) {
        Connection connection = dbConnection.getConnection();
        List<Reservation> cancelledReservations = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM reservations WHERE user_id = ? AND cancellation_date IS NOT NULL"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int eventId = rs.getInt("event_id");
                int seatsReserved = rs.getInt("seats_reserved");
                String reservationDate = rs.getString("reservation_date");
                String cancellationDate = rs.getString("cancellation_date");

                Reservation reservation = new Reservation(id, userId, eventId, seatsReserved, reservationDate, cancellationDate);
                cancelledReservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cancelledReservations;
    }

    public List<Reservation> getUserReservations(int userId) {
        Connection connection = dbConnection.getConnection();
        List<Reservation> reservations = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT r.id, r.user_id, r.event_id, r.seats_reserved, r.reservation_date, r.cancellation_date, e.date, e.time " +
                            "FROM reservations r JOIN events e ON r.event_id = e.id " +
                            "WHERE r.user_id = ? AND e.date > ? AND r.cancellation_date IS NULL"
            );
            ps.setInt(1, userId);
            ps.setString(2, LocalDate.now().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int eventId = rs.getInt("event_id");
                int seatsReserved = rs.getInt("seats_reserved");
                String reservationDate = rs.getString("reservation_date");
                String cancellation_date = rs.getString("cancellation_date");

                // Construim data și ora evenimentului pentru a verifica dacă este în viitor
                String eventDateStr = rs.getString("date");
                String eventTimeStr = rs.getString("time");
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                LocalDate eventDate = LocalDate.parse(eventDateStr, dateFormatter);
                LocalTime eventTime = LocalTime.parse(eventTimeStr, timeFormatter);
                LocalDateTime eventDateTime = LocalDateTime.of(eventDate, eventTime);

                if (eventDateTime.isAfter(LocalDateTime.now())) {
                    Reservation reservation = new Reservation(id, eventId, userId, seatsReserved, reservationDate, cancellation_date);
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public void cancelReservation(int reservationId) {
        Connection connection = dbConnection.getConnection();
        try {
            connection.setAutoCommit(false);

            // Get the reservation details
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT event_id, seats_reserved FROM reservations WHERE id = ?"
            );
            selectStatement.setInt(1, reservationId);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                int eventId = rs.getInt("event_id");
                int seatsReserved = rs.getInt("seats_reserved");

                // Update the cancellation date
                PreparedStatement updateStatement = connection.prepareStatement(
                        "UPDATE reservations SET cancellation_date = ? WHERE id = ?"
                );

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedDateTime = now.format(formatter);

                updateStatement.setString(1, formattedDateTime);
                updateStatement.setInt(2, reservationId);

                updateStatement.executeUpdate();

                // Update the available seats in the event
                updateSeats(eventId, seatsReserved, connection);

                connection.commit();

                System.out.println("Reservation canceled successfully.");
            } else {
                System.out.println("Reservation not found.");
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            System.err.println("Error canceling reservation: " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateSeats(int eventId, int seats, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE events SET available_seats = available_seats + ? WHERE id = ?"
        );

        ps.setInt(1, seats);
        ps.setInt(2, eventId);

        ps.executeUpdate();
    }
    public String getReservationInfoById(int reservationId) {
        Connection connection = dbConnection.getConnection();
        String reservationInfo = "Reservation not found.";
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT r.id, r.user_id, r.event_id, r.seats_reserved, r.reservation_date, e.name, e.date, e.time, e.location_id, e.category " +
                            "FROM reservations r JOIN events e ON r.event_id = e.id " +
                            "WHERE r.id = ?"
            );
            ps.setInt(1, reservationId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                int eventId = rs.getInt("event_id");
                int seatsReserved = rs.getInt("seats_reserved");
                String reservationDate = rs.getString("reservation_date");
                String eventName = rs.getString("name");
                String eventDate = rs.getString("date");
                String eventTime = rs.getString("time");
                int locationId = rs.getInt("location_id");
                EventCategory category = EventCategory.valueOf(rs.getString("category"));

                UserDAO userDAO = new UserDAO();
                User user = userDAO.getUserById(userId);
                String username = (user != null) ? user.getUsername() : "Unknown User";

                LocationDAO locationDAO = new LocationDAO();
                Location location = locationDAO.getLocationById(locationId);
                String locationDetails = (location != null) ? location.getName() + ", " + location.getAddress() : "Unknown Location";

                reservationInfo = String.format(
                        "Reservation ID: %d\nSeats Reserved: %d\nReservation Date: %s\n" +
                                "Event Name: %s\nEvent Date: %s\nEvent Time: %s\nLocation: %s\nCategory: %s",
                        reservationId, seatsReserved, reservationDate,
                        eventName, eventDate, eventTime, locationDetails, category
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservationInfo;
    }
    public int getCancellationsLastMonth(int userId) {
        Connection connection = dbConnection.getConnection();
        int cancellationCount = 0;

        try {
            LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT COUNT(*) AS cancellation_count FROM reservations WHERE user_id = ? AND cancellation_date >= ?"
            );
            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(oneMonthAgo));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cancellationCount = rs.getInt("cancellation_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cancellationCount;
    }

    public Map<String, Object> getMapCancellationsLastMonth(int userId) {
        Connection connection = dbConnection.getConnection();
        int cancellationCount = 0;
        LocalDate lastCancellationDate = null;

        try {
            LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT COUNT(*) AS cancellation_count, MAX(cancellation_date) AS last_cancellation_date " +
                            "FROM reservations WHERE user_id = ? AND cancellation_date >= ?"
            );
            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(oneMonthAgo));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cancellationCount = rs.getInt("cancellation_count");
                Date lastCancellationDateSql = rs.getDate("last_cancellation_date");
                if (lastCancellationDateSql != null) {
                    lastCancellationDate = lastCancellationDateSql.toLocalDate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("cancellationCount", cancellationCount);
        result.put("lastCancellationDate", lastCancellationDate);
        return result;
    }

//    public Map<String, Object> getCancellationsLastMonth(int userId) {
//        Map<String, Object> result = new HashMap<>();
//        Connection connection = dbConnection.getConnection();
//        try {
//            PreparedStatement ps = connection.prepareStatement(
//                    "SELECT COUNT(*) AS cancellationCount, MAX(cancellation_date) AS lastCancellationDate " +
//                            "FROM reservations " +
//                            "WHERE user_id = ? AND cancellation_date > CURRENT_DATE - INTERVAL '1 month'"
//            );
//            ps.setInt(1, userId);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                int cancellationCount = rs.getInt("cancellationCount");
//                String lastCancellationDate = rs.getString("lastCancellationDate");
//                result.put("cancellationCount", cancellationCount);
//                result.put("lastCancellationDate", lastCancellationDate != null ? LocalDate.parse(lastCancellationDate) : null);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

}

