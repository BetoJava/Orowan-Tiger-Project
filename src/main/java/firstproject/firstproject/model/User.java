package firstproject.firstproject.model;

public class User {

    public static String WORKER = "worker";
    public static String ENGINEER = "engineer";

    private int id;
    private String identifier;
    private String password;
    private String role;

    public User(int id, String identifier, String password, String role) {
        this.id = id;
        this.identifier = identifier;
        this.password = password;
        this.role = role;
    }

    public User(int id, String identifier, String password) {
        this(id, identifier, password, WORKER);
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
