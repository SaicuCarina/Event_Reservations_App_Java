package MODELS;

import DAO.UserDAO;
/*import DAO.ReservationDAO;*/
import DAO.MyDBConnection;

/*import DAO.EventDAO;*/
import java.util.List;
import java.util.Scanner;


   /* public static void main(String[] args) {
        App app = new App();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Log in");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                if (app.login(email, password)) {
                    System.out.println("Login successful!");
                    // Continue to reservation or other functionalities
                } else {
                    System.out.println("Invalid email or password.");
                }
            } else if (choice == 2) {
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();

                if (app.register(username, password, email)) {
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("Registration failed. Username or email already exists.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }*/
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
               scanner.nextLine(); // Consumăm newline

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

                           // Verificăm dacă username-ul este deja folosit
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

                           // Verificăm dacă username-ul este deja folosit
                           isUsernameTaken = app.isUsernameTaken(username);
                           if (isUsernameTaken) {
                               System.out.println("Username already taken. Please choose another one.");
                           }
                       } while (isUsernameTaken);

                       System.out.print("Enter password: ");
                       String password = scanner.nextLine();
                       // Creăm un nou utilizator și îl adăugăm
                       User newUser = new User(0, email, username, password);
                       app.addUser(newUser);

                       System.out.println("User registered successfully.");
                       break;
                   case 3:
                       System.out.println("Exiting...");
                       isRunning = false; // Ieșim din buclă și din program
                       break;
                   default:
                       System.out.println("Invalid option. Please choose again.");
                       break;
               }
           }
       }
       private static void showReservationMenu(Scanner scanner, User currentUser) {
           while (true) {
               System.out.println("Reservation Menu:");
               System.out.println("1. Make a reservation");
               System.out.println("2. View reservations");
               System.out.println("3. Logout");
               System.out.print("Choose an option: ");
               int option = scanner.nextInt();
               scanner.nextLine(); // Consume newline

               switch (option) {
                   case 1:
                       // Implement logic for making a reservation
                       System.out.println("Reservation made.");
                       break;
                   case 2:
                       // Implement logic for viewing reservations
                       System.out.println("Viewing reservations.");
                       break;
                   case 3:
                       System.out.println("Logging out...");
                       return;
                   default:
                       System.out.println("Invalid option. Please try again.");
               }
           }
       }
   }