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
        Connection connection = dbConnection.getConnection();
        List<Location> locationList = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM locations");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                int capacity = rs.getInt("capacity");

                Location location = new Location(id, name, address, capacity);
                locationList.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locationList;
    }
    public Location getLocationById(int Id){
        Connection connection = dbConnection.getConnection();
        Location foundLocation = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM locations WHERE id = ?");
            ps.setInt(1, Id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                int capacity = rs.getInt("capacity");

                foundLocation = new Location(id, name, address, capacity);

            }
        } catch (SQLException e){
            System.err.println("Error retrieving location from the database: " + e.getMessage());
        }
        return foundLocation;
    }
    public Location getLocationByName(String name){
        Connection connection = dbConnection.getConnection();
        Location foundLocation = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM locations WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int id = rs.getInt("id");
                String nameLocation = rs.getString("name");
                String address = rs.getString("address");
                int capacity = rs.getInt("capacity");

                foundLocation = new Location(id, nameLocation, address, capacity);

            }
        } catch (SQLException e){
            System.err.println("Error retrieving location from the database: " + e.getMessage());
        }
        return foundLocation;
    }
}
