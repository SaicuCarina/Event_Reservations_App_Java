package MODELS;
/*import DAO.EventDAO;*/
import DAO.EventDAO;
import DAO.LocationDAO;
import DAO.UserDAO;
import DAO.ReservationDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class App implements Search, Reserve{
    private List<User> users;
    private List<Event> events;
    private List<Location> locations;
    private List<Reservation> reservations;

    public App() {
        this.users = new ArrayList<>();
        this.events = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.reservations = new ArrayList<>();
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
            return false;
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

    @Override
    public User searchUserByUsernameAndPassword(String username, String password) {
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

    @Override
    public Location searchLocationById(int id){
        LocationDAO locationDAO = new LocationDAO();
        Location location = locationDAO.getLocationById(id);
        if (location != null) {
            return location;
        } else {
            return null;
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
    @Override
    public void reserveEvent(User user, Event event, int seatsReserved) {
        if (event.getAvailableSeats() >= seatsReserved) {
            ReservationDAO reservationDAO = new ReservationDAO();
            EventDAO eventDAO = new EventDAO();
            reservationDAO.reserveEvent(user.getId(), event.getId(), seatsReserved);
            event.setAvailableSeats(event.getAvailableSeats() - seatsReserved);
            eventDAO.updateSeats(event.getId(), seatsReserved);
            System.out.println("Reservation successful for event: " + event.getName());
            System.out.println("Dress Code: " + event.getDressCode().getDressCodeMessage());
        } else {
            System.out.println("Not enough available seats for event: " + event.getName());
        }
    }

    @Override
    public List<Reservation> getUserReservations(User user) {
        ReservationDAO reservationDAO = new ReservationDAO();
        return reservationDAO.getUserReservations(user.getId());
    }

    @Override
    public void cancelReservation(int reservationId) {
        ReservationDAO reservationDAO = new ReservationDAO();
        reservationDAO.cancelReservation(reservationId);
    }
    @Override
    public String getReservationInfo(int reservationId) {
        ReservationDAO reservationDAO = new ReservationDAO();
        return reservationDAO.getReservationInfoById(reservationId);
    }
    @Override
    public Location searchEventsByLocationName(String locationName) {
        LocationDAO locationDAO = new LocationDAO();
        Location location = locationDAO.getLocationByName(locationName);
        if (location != null) {
            EventDAO eventDAO = new EventDAO();
            List<Event> events = eventDAO.getEventsByLocation(location.getId());
            System.out.println("Events at location: " + location.getName() + ", " + location.getAddress());
            for (Event event : events) {
                if (isEventAvailable(event)) {
                    System.out.println(event);
                }
            }
        } else {
            System.out.println("Location not found.");
        }
        return location;
    }

    @Override
    public List<Event> searchEventsByDate(String date) {
        EventDAO eventDAO = new EventDAO();
        return eventDAO.getEventsByDate(date);
    }

    @Override
    public List<Event> searchEventByCategory(EventCategory category) {
        EventDAO eventDAO = new EventDAO();
        return eventDAO.searchEventByCategory(category);
    }

}

