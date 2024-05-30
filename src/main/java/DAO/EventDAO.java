package DAO;

import MODELS.Location;
import MODELS.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private MyDBConnection dbConnection;

    public EventDAO() {
        dbConnection = MyDBConnection.getInstance();
    }

    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        String query = "SELECT * FROM events";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Location location = findLocationById(rs.getInt("location_id"));
                Event event = new Event(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("dateTime").toLocalDateTime(), location, rs.getInt("availableSeats"));
                eventList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventList;
    }

    private Location findLocationById(int locationId) {
        // Implementați căutarea locației după ID
        return new LocationDAO().getLocationById(locationId);
    }
}
