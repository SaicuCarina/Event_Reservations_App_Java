package MODELS;
import java.util.Scanner;

public class LoginMenu {
    private App app;
    private Scanner scanner;

    public LoginMenu(App app) {
        this.app = app;
        this.scanner = new Scanner(System.in);
    }

    public void displayLoginMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (true) {
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
                        Menu menu = new Menu(app);
                        menu.displayMenu(currentUser);
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
}
