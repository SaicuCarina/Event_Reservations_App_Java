package DAO;

import MODELS.Location;
import MODELS.Event;

import java.sql.*;
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

/*    private static Location findLocationById(int location_id) {
        LocationDAO locationDAO = new LocationDAO();
        return locationDAO.getLocationById(location_id);
    }*/
}
