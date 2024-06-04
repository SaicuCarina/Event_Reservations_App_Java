package MODELS;

import DAO.*;
/*import DAO.ReservationDAO;*/

import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;

   public class Main {

       public static void main(String[] args) {
           App app = new App();
           Scanner scanner = new Scanner(System.in);
           boolean isRunning = true;

           while (isRunning) {
               System.out.println("Menu:");
               System.out.println("1. Login");
               System.out.println("2. Register");
               System.out.println("3. Exit");
               System.out.print("Choose an option: ");
               int option = scanner.nextInt();
               scanner.nextLine();

               switch (option) {
                   case 1:
                       System.out.println("Login:");
                       System.out.print("Enter username: ");
                       String loginUsername = scanner.nextLine();
                       System.out.print("Enter password: ");
                       String loginPassword = scanner.nextLine();
                       User currentUser = app.searchUserByUsernameAndPassword(loginUsername, loginPassword);
                       if (currentUser != null) {
                           System.out.println("Login successful.");
                           showReservationMenu(scanner, currentUser);
                       } else {
                           System.out.println("Login failed. Please check your username and password.");
                       }
                       break;
                   case 2:
                       System.out.println("Register:");

                       String email;
                       boolean isEmailTaken;
                       do {
                           System.out.print("Enter email: ");
                           email = scanner.nextLine();

                           isEmailTaken = app.isEmailTaken(email);
                           if (isEmailTaken) {
                               System.out.println("This email is already registered. Please enter a different one.");
                           }
                       } while (isEmailTaken);

                       String username;
                       boolean isUsernameTaken;
                       do {
                           System.out.print("Enter username: ");
                           username = scanner.nextLine();

                           isUsernameTaken = app.isUsernameTaken(username);
                           if (isUsernameTaken) {
                               System.out.println("Username already taken. Please choose another one.");
                           }
                       } while (isUsernameTaken);

                       System.out.print("Enter password: ");
                       String password = scanner.nextLine();
                       User newUser = new User(0, email, username, password);
                       app.addUser(newUser);

                       System.out.println("User registered successfully.");
                       break;
                   case 3:
                       System.out.println("Exiting...");
                       isRunning = false;
                       break;
                   default:
                       System.out.println("Invalid option. Please choose again.");
                       break;
               }
           }
       }
       private static void showReservationMenu(Scanner scanner, User currentUser) {
           User Current = currentUser;
           App app = new App();
           while (true) {
               System.out.println("Reservation Menu:");
               System.out.println("1. Show all events");
               System.out.println("2. Show all locations");
               System.out.println("3. Search location by id"); // Search
               System.out.println("4. Search events from a specific location"); // Search
               System.out.println("5. Show all locations with the events");
               System.out.println("6. Search events by date"); // Search
               System.out.println("7. Search events by category"); // Search
               System.out.println("8. Make reservation");
               System.out.println("9. Show your reservations");
               System.out.println("10. Show reservation information");
               System.out.println("11. Cancel a reservation");
               System.out.println("12. Report!");
               System.out.println("13. Logout");
               System.out.print("Choose an option: ");
               int option = scanner.nextInt();
               scanner.nextLine();

               switch (option) {
                   case 1:
                       System.out.println("Available events:");
                       EventDAO eventDAO = new EventDAO();
                       List<Event> eventsFromDB = eventDAO.getAllEventsFromDB();
                       for (Event event : eventsFromDB) {
                           if (App.isEventAvailable(event)) {
                               System.out.println(event);
                           }
                       }
                       break;
                   case 2:
                       System.out.println("All the locations:");
                       LocationDAO locationDAO = new LocationDAO();
                       List<Location> locationsFromDB = locationDAO.getAllLocations();
                       for (Location location : locationsFromDB) {
                           System.out.println(location);
                       }
                       break;
                   case 3:
                       System.out.println("Enter location id:");
                       int locationId = scanner.nextInt();
                       Location location;
                       location = app.searchLocationById(locationId);
                       if(location == null)
                           System.out.println("There isn't a location with this ID.");
                       else
                           System.out.println(location);
                       break;
                   case 4:
                       System.out.println("Enter location name:");
                       String locationName = scanner.nextLine();
                       app.searchEventsByLocationName(locationName);
                       /*if (location1 != null) {
                           System.out.println("Location found: " + location1.getName() + ", " + location1.getAddress());
                       }*/
                       break;
                   case 5:
                       App.showEventsByLocation();
                       break;
                   case 6:
                       System.out.println("Enter date (yyyy-MM-dd) to view events:");
                       String date = scanner.nextLine();
                       List<Event> eventsByDate = app.searchEventsByDate(date);
                       if (!eventsByDate.isEmpty()) {
                           System.out.println("Events on " + date + ":");
                           for (Event event : eventsByDate) {
                               System.out.println(event);
                           }
                       } else {
                           System.out.println("No events found for the specified date.");
                       }
                       break;
                   case 7:
                       System.out.println("Select category to view events:");
                       EventCategory[] categories = EventCategory.values();
                       for (int i = 0; i < categories.length; i++) {
                           System.out.println((i + 1) + ". " + categories[i]);
                       }
                       System.out.print("Enter category number: ");
                       int categoryNumber = scanner.nextInt();
                       scanner.nextLine();

                       if (categoryNumber < 1 || categoryNumber > categories.length) {
                           System.out.println("Invalid category number.");
                           break;
                       }

                       EventCategory category = categories[categoryNumber - 1];
                       List<Event> eventsByCategory = app.searchEventByCategory(category);
                       if (!eventsByCategory.isEmpty()) {
                           System.out.println("Events in category " + category + ":");
                           for (Event event : eventsByCategory) {
                               System.out.println(event);
                           }
                       } else {
                           System.out.println("No events found for the specified category.");
                       }
                       break;
                   case 8:

                       boolean check = app.check(currentUser);
                       if(!check){
                           break;
                       }

                       System.out.println("Enter event ID to reserve:");
                       int eventId = scanner.nextInt();
                       scanner.nextLine();
                       Event event = null;
                       EventDAO eventDAO2 = new EventDAO();
                       List<Event> eventsFromDB2 = eventDAO2.getAllEventsFromDB();
                       for (Event e : eventsFromDB2) {
                           if (e.getId() == eventId) {
                               event = e;
                               break;
                           }
                       }
                       if (event != null) {
                           System.out.println("Enter number of seats to reserve:");
                           int seatsReserved = scanner.nextInt();
                           scanner.nextLine();
                           app.reserveEvent(currentUser, event, seatsReserved);
                       } else {
                           System.out.println("Event not found.");
                       }
                       break;
                   case 9:
                       System.out.println("Your reservations:");
                       List<Reservation> reservations = app.getUserReservations(currentUser);
                       for (Reservation reservation : reservations) {
                           System.out.println(reservation);
                       }
                       break;
                   case 10:
                       System.out.println("Enter reservation ID to view information:");
                       int resId = scanner.nextInt();
                       scanner.nextLine();
                       String reservationInfo = app.getReservationInfo(resId);
                       System.out.println(reservationInfo);
                       break;
                   case 11:
                       System.out.println("Enter reservation ID to cancel:");
                       int reservationId = scanner.nextInt();
                       scanner.nextLine();
                       app.cancelReservation(reservationId);
                       break;
                   case 12:
                       break;
                   case 13:
                       System.out.println("Logging out...");
                       return;
                   default:
                       System.out.println("Invalid option. Please try again.");
               }
           }
       }
   }