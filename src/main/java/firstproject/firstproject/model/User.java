package firstproject.firstproject.model;

import java.util.ArrayList;

public class User {

    public static String WORKER = "Worker";
    public static String ENGINEER = "Engineer";

    private int id;
    private String identifier;
    private String password;
    private String role;

    private ArrayList<Stand> standList = new ArrayList<>();

    public User(){}

    public User(int id, String identifier, String password, String role) {
        this.id = id;
        this.identifier = identifier;
        this.password = password;
        this.role = role;
    }

    public User(int id, String identifier, String password) {
        this(id, identifier, password, WORKER);
    }

    /**
     * Renvoie si l'utilisateur a au moins un stand.
     */
    public boolean hasStand(String stand){
        for (Stand s : standList)
            if(s.getStandID() == stand)
                return true;

        return false;
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

    public ArrayList<Stand> getStandList() {
        return standList;
    }

    public void setStandList(ArrayList<Stand> standList){ this.standList = standList; }

    public boolean isEngineer(){ return role == User.ENGINEER; }
}
