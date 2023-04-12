package firstproject.firstproject.model;

import java.util.ArrayList;

public class Stand {

    private String standID; //F2
    private ArrayList<Strip> stripList = new ArrayList<>();
    private Boolean enabled;

    public Stand(String standID, ArrayList<Strip> stripList, Boolean enabled) {
        this.standID = standID;
        this.stripList = stripList;
        this.enabled = enabled;
    }

    public ArrayList<Strip> getStripList() {
        return stripList;
    }

    public String getStandID() {
        return standID;
    }
    public Boolean isEnabled(){ return enabled; }
}
