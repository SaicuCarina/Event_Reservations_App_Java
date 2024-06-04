package MODELS;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;


    public User(int id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
    public void setId(int id) {
        this.id = id;
    }

}