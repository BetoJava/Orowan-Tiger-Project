package firstproject.firstproject.model;

import java.util.ArrayList;

public class Stand {

    private String standID; //F2
    private ArrayList<Strip> stripList = new ArrayList<>();

    public Stand(String standID) {
        this.standID = standID;
    }

    public ArrayList<Strip> getStripList() {
        return stripList;
    }

    public String getStandID() {
        return standID;
    }
}
