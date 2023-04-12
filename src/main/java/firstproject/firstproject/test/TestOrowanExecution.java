package firstproject.firstproject.test;

import firstproject.firstproject.model.Orowan;
import firstproject.firstproject.model.OrowanOutputData;

import java.util.ArrayList;

public class TestOrowanExecution {

    public static void main(String[] args) {
        ArrayList<OrowanOutputData> outputData = Orowan.computeOrowanModel("1939351","F2");
    }


}
