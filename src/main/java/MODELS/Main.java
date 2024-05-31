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

                       break;
                   default:
                       System.out.println("Invalid option. Please choose again.");
                       break;
               }
           }
       }
       private static void showReservationMenu(Scanner scanner, User currentUser) {
           App app = new App();
           while (true) {
               System.out.println("Reservation Menu:");
               System.out.println("1. Show all events");
               System.out.println("2. Make a reservation");
               System.out.println("3. View reservations");
               System.out.println("4. Logout");
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
                       // Implement logic for making a reservation
                       System.out.println("Reservation made.");
                       break;
                   case 3:
                       // Implement logic for viewing reservations
                       System.out.println("Viewing reservations.");
                       break;
                   case 4:
                       System.out.println("Logging out...");
                       return;
                   default:
                       System.out.println("Invalid option. Please try again.");
               }
           }
       }
   }