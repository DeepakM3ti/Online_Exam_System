package exam;

public class Teacher {

    private String username;
    private String password;

    public Teacher(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login() {
        return username.equals("teacher") &&
               password.equals("teacher123");
    }

    public String getUsername() {
        return username;
    }
}