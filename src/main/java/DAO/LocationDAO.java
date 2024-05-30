package DAO;

import MODELS.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO {
    private MyDBConnection dbConnection;

    public LocationDAO() {
        dbConnection = MyDBConnection.getInstance();
    }

    public List<Location> getAllLocations() {
        List<Location> locationList = new ArrayList<>();
        String query = "SELECT * FROM locations";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Location location = new Location(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getInt("capacity"));
                locationList.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locationList;
    }

    public Location getLocationById(int id) {
        String query = "SELECT * FROM locations WHERE id = ?";
        Location location = null;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    location = new Location(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getInt("capacity"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return location;
    }
}
