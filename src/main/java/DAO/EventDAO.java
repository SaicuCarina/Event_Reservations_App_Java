package DAO;

import MODELS.Location;
import MODELS.Event;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class EventDAO {
    private MyDBConnection dbConnection;

    public EventDAO() {
        dbConnection = MyDBConnection.getInstance();
    }

    public List<Event> getAllEventsFromDB() {
        Connection connection = dbConnection.getConnection();
        List<Event> eventList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM events");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String date = rs.getString("date");
                String time = rs.getString("time");
                int location_id = rs.getInt("location_id");
                int available_seats = rs.getInt("available_seats");

                Event event = new Event(id, name, date, time, location_id, available_seats);
                eventList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventList;
    }

    public void updateSeats(int eventId, int seatsReserved) {
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE events SET available_seats = available_seats - ? WHERE id = ?");
            // Set the values for the placeholders
            ps.setInt(1, seatsReserved);
            ps.setInt(2, eventId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error retrieving location from the database: " + e.getMessage());
        }

    }
}

