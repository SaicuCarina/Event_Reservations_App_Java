package DAO;

import DAO.MyDBConnection;
import org.example.SimpleUser;
import MODELS.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private MyDBConnection dbConnection;

    public UserDAO() {
        dbConnection = MyDBConnection.getInstance();
    }

    public List<User> getUsersFromDB() {
        Connection connection = dbConnection.getConnection();
        List<User> userList = new ArrayList<>();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");

                User user = new User(id, email, username, password);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void addUserToDB(User user) {
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO users (email, username, password) VALUES (?, ?, ?)"
            );

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();

            System.out.println("User added to the database.");
        } catch (SQLException e) {
            System.err.println("Error adding user to the database: " + e.getMessage());
        }
    }
    public User getUserById(int userId) {
        Connection connection = dbConnection.getConnection();
        User user = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}