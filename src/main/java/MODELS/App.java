package MODELS;
/*import DAO.EventDAO;*/
import DAO.MyDBConnection;
import DAO.UserDAO;
/*import DAO.ReservationDAO;
import DAO.LocationDAO;*/

import java.util.ArrayList;
import java.util.List;

public class App {
    private List<User> users;

    public App() {
        this.users = new ArrayList<>();
        loadUsersFromDB();
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

    public User findUserByUsernameAndPassword(String username, String password) {
        // Verificăm în lista curentă de utilizatori
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user; // Utilizatorul a fost găsit în listă
            }
        }

        // Cautăm în baza de date dacă nu am găsit utilizatorul în lista curentă
        UserDAO userDAO = new UserDAO();
        List<User> usersFromDB = userDAO.getUsersFromDB();
        for (User user : usersFromDB) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                // Adăugăm utilizatorul găsit în lista aplicației
                users.add(user);
                return user; // Utilizatorul a fost găsit în baza de date
            }
        }

        return null; // Utilizatorul nu a fost găsit nici în listă, nici în baza de date
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

    /*public void addUserFromDatabase() {
        // Fetch people from the database using PersonDAO
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getUsersFromDB();

        // Add fetched bikes to the shop
        for (User user : users) {
            addPersonFromDB(user);
        }
    }

    public void addPersonFromDB(User user) {
         users.add(user);
    }

    public void addUser(User user) {
        UserDAO userDAO = new UserDAO();
        if (user instanceof Admin) {
            if(person.validateEmail(person.getEmail())){
                admins.add((Admin) person);
                people.add(person);
                personDAO.addUserToDB(person);
            } else{
                System.out.println("Invalid email");
            }

        } else if (person instanceof User) {
            if(person.validateEmail(person.getEmail())){
                users.add((User) person);
                people.add(person);
                personDAO.addUserToDB(person);
            } else{
                System.out.println("Invalid email");
            }
        }
        public User searchUserByEmailPassword (String email, String password){
            User foundUser = null;
            for (User user : users) {
                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    foundUser = user;
                }
            }
            return foundUser;
        }

        public boolean isUserFound(User foundUser){
            if (foundUser == null){
                return false;
            } else{
                return true;
            }
        }*/




    /*public void addUserToDB(User user) {
        UserDAO userDAO = new UserDAO();
        if (userDAO.registerUser(user.getUsername(), user.getPassword(), user.getEmail())) {
            int id = userDAO.getLastInsertedId();
            user.setId(id);
            users.add(user);
        }
    }

    public void addUser(User user) {
        addUserToDB(user);
    }

    public User searchUserByEmailPassword(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean isUserFound(User foundUser) {
        return foundUser != null;
    }

    public boolean login(String email, String password) {
        User foundUser = searchUserByEmailPassword(email, password);
        return isUserFound(foundUser);
    }

    public boolean register(String username, String password, String email) {
        for (User user : users) {
            if (user.getUsername().equals(username) || user.getEmail().equals(email)) {
                return false;  // User already exists
            }
        }
        User newUser = new User(0, username, password, email);
        addUser(newUser);
        return true;
    }*/
