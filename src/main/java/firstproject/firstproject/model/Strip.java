package firstproject.firstproject.model;

import firstproject.firstproject.dataClasses.OrowanOutputData;

import java.util.ArrayList;

public class Strip {

    private int stripID;

    private ArrayList<OrowanOutputData> orowanOutputDataList = new ArrayList<>();

    public Strip(int stripID) {
        this.stripID = stripID;
    }

    public int getStripID() {
        return stripID;
    }

    public void setStripID(int stripID) {
        this.stripID = stripID;
    }
}
