package firstproject.firstproject.model;

import java.util.ArrayList;

public class Stand {

    private String standID;
    private boolean isEnable;
    private ArrayList<Strip> stripList = new ArrayList<>();

    public Stand(String standID) {
        this.standID = standID;
        this.isEnable = true;
    }

    public Stand(String standID, ArrayList<Strip> stripList, boolean isEnable) {
        this.standID = standID;
        this.stripList = stripList;
        this.isEnable = isEnable;
    }

    public ArrayList<Strip> getStripList() {
        return stripList;
    }

    public String getStandID() {
        return standID;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
