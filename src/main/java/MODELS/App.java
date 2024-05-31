package MODELS;
/*import DAO.EventDAO;*/
import DAO.EventDAO;
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

public class App {
    private List<User> users;
    private List<Event> events;

    public App() {
        this.users = new ArrayList<>();
        /*loadUsersFromDB();*/
        this.events = new ArrayList<>();
/*        loadEventsFromDB();*/
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
}

