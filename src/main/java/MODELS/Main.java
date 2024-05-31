package MODELS;

import DAO.UserDAO;
/*import DAO.ReservationDAO;*/
import DAO.MyDBConnection;

import DAO.EventDAO;
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
                       User currentUser = app.findUserByUsernameAndPassword(loginUsername, loginPassword);
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
                   case 4:
                       App.showEventsByLocation();
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
               System.out.println("2. Show location by id");
               System.out.println("3. Show all locations with the events");
               System.out.println("4. Make reservation");
               System.out.println("5. Show your reservations");
               System.out.println("6. Show reservation information");
               System.out.println("7. Cancel a reservation");
               System.out.println("8. Report!");
               System.out.println("9. Logout");
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
                       System.out.println("Enter location id:");
                       int locationId = scanner.nextInt();
                       App.showLocation(locationId);
                       break;

                   case 3:
                       App.showEventsByLocation();
                       break;
                   case 4:
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
                   case 5:
                       System.out.println("Your reservations:");
                       List<Reservation> reservations = app.getUserReservations(currentUser);
                       for (Reservation reservation : reservations) {
                           System.out.println(reservation);
                       }
                       break;
                   case 6:
                       System.out.println("Enter reservation ID to view information:");
                       int resId = scanner.nextInt();
                       scanner.nextLine();
                       String reservationInfo = app.getReservationInfo(resId);
                       System.out.println(reservationInfo);
                       break;
                   case 7:
                       System.out.println("Enter reservation ID to cancel:");
                       int reservationId = scanner.nextInt();
                       scanner.nextLine();
                       app.cancelReservation(reservationId);
                       break;
                   case 8:
                       break;
                   case 9:
                       System.out.println("Logging out...");
                       return;
                   default:
                       System.out.println("Invalid option. Please try again.");
               }
           }
       }
   }