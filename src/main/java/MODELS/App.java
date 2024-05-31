package MODELS;
/*import DAO.EventDAO;*/
import DAO.EventDAO;
import DAO.LocationDAO;
import DAO.MyDBConnection;
import DAO.UserDAO;
/*import DAO.ReservationDAO;
import DAO.LocationDAO;*/

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class App {
    private List<User> users;
    private List<Event> events;
    private List<Location> locations;

    public App() {
        this.users = new ArrayList<>();
        /*loadUsersFromDB();*/
        this.events = new ArrayList<>();
/*        loadEventsFromDB();*/
        this.locations = new ArrayList<>();
    }

    private void loadUsersFromDB() {
        UserDAO userDAO = new UserDAO();
        users = userDAO.getUsersFromDB();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    private void loadEventsFromDB() {
        EventDAO eventDAO = new EventDAO();
        events = eventDAO.getAllEventsFromDB();
    }

    public static boolean isEventAvailable(Event event) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate eventDate;
        LocalTime eventTime;
        try {
            eventDate = LocalDate.parse(event.getDate(), dateFormatter);
            eventTime = LocalTime.parse(event.getTime(), timeFormatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false; // Formatul datei sau al orei este invalid
        }

        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        if (eventDate.isAfter(nowDate)) {
            return event.getAvailableSeats() > 0;
        } else if (eventDate.isEqual(nowDate)) {
            return eventTime.isAfter(nowTime) && event.getAvailableSeats() > 0;
        } else {
            return false;
        }
    }


    public User findUserByUsernameAndPassword(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        UserDAO userDAO = new UserDAO();
        List<User> usersFromDB = userDAO.getUsersFromDB();
        for (User user : usersFromDB) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                users.add(user);
                return user;
            }
        }
        return null;
    }
    public boolean isUsernameTaken(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmailTaken(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.addUserToDB(user); // Adăugăm utilizatorul în baza de date

        // Adăugăm utilizatorul în lista aplicației
        users.add(user);
    }

    public static void showLocation(int id){
        LocationDAO locationDAO = new LocationDAO();
        Location location = locationDAO.getLocationById(id);
        if (location != null) {
            System.out.println(location);
        } else {
            System.out.println("Location not found.");
        }
    }
    public static void showEventsByLocation() {
        LocationDAO locationDAO = new LocationDAO();
        EventDAO eventDAO = new EventDAO();

        List<Location> locations = locationDAO.getAllLocations();
        List<Event> events = eventDAO.getAllEventsFromDB();

        for (Location location : locations) {
            System.out.println("Location: " + location.getName() + ", Address: " + location.getAddress());
            boolean hasEvents = false;

            for (Event event : events) {
                if (event.getLocationId() == location.getId() && isEventAvailable(event)) {
                    System.out.println("  " + event);
                    hasEvents = true;
                }
            }

            if (!hasEvents) {
                System.out.println("  No available events for this location.");
            }
        }
    }
}

