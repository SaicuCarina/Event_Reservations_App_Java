package DAO;

import MODELS.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
                EventCategory category = EventCategory.valueOf(rs.getString("category"));
                DressCode dressCode;

                switch (category) {
                    case show:
                        dressCode = new DressCodeShow();
                        break;
                    case concert:
                        dressCode = new DressCodeConcert();
                        break;
                    case conference:
                        dressCode = new DressCodeConference();
                        break;
                    default:
                        throw new IllegalStateException("Unknown category: " + category);
                }

                Event event = new Event(id, name, date, time, location_id, available_seats, category, dressCode);
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
            ps.setInt(1, seatsReserved);
            ps.setInt(2, eventId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error retrieving location from the database: " + e.getMessage());
        }

    }
    public List<Event> getEventsByLocation(int locationId) {
        Connection connection = dbConnection.getConnection();
        List<Event> eventList = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM events WHERE location_id = ?");
            ps.setInt(1, locationId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String date = rs.getString("date");
                String time = rs.getString("time");
                int location_id = rs.getInt("location_id");
                int available_seats = rs.getInt("available_seats");
                EventCategory category = EventCategory.valueOf(rs.getString("category"));

                DressCode dressCode;
                switch (category) {
                    case show:
                        dressCode = new DressCodeShow();
                        break;
                    case concert:
                        dressCode = new DressCodeConcert();
                        break;
                    case conference:
                        dressCode = new DressCodeConference();
                        break;
                    default:
                        throw new IllegalStateException("Unknown category: " + category);
                }

                Event event = new Event(id, name, date, time, location_id, available_seats, category, dressCode);
                eventList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventList;
    }
    public List<Event> getEventsByDate(String date) {
        Connection connection = dbConnection.getConnection();
        List<Event> eventList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM events WHERE date = ?");
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String dateFromDB = rs.getString("date");
                String time = rs.getString("time");
                int location_id = rs.getInt("location_id");
                int available_seats = rs.getInt("available_seats");
                EventCategory category = EventCategory.valueOf(rs.getString("category"));

                DressCode dressCode;
                switch (category) {
                    case show:
                        dressCode = new DressCodeShow();
                        break;
                    case concert:
                        dressCode = new DressCodeConcert();
                        break;
                    case conference:
                        dressCode = new DressCodeConference();
                        break;
                    default:
                        throw new IllegalStateException("Unknown category: " + category);
                }

                Event event = new Event(id, name, date, time, location_id, available_seats, category, dressCode);
                eventList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventList;
    }
    public List<Event> searchEventByCategory(EventCategory  category) {
        Connection connection = dbConnection.getConnection();
        List<Event> eventList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM events WHERE category = ?");
            ps.setString(1, category.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String date = rs.getString("date");
                String time = rs.getString("time");
                int location_id = rs.getInt("location_id");
                int available_seats = rs.getInt("available_seats");
                EventCategory eventCategory = EventCategory.valueOf(rs.getString("category"));

                DressCode dressCode;
                switch (category) {
                    case show:
                        dressCode = new DressCodeShow();
                        break;
                    case concert:
                        dressCode = new DressCodeConcert();
                        break;
                    case conference:
                        dressCode = new DressCodeConference();
                        break;
                    default:
                        throw new IllegalStateException("Unknown category: " + category);
                }

                Event event = new Event(id, name, date, time, location_id, available_seats, category, dressCode);
                eventList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventList;
    }
}

