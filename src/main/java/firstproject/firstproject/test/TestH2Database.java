package firstproject.firstproject.test;

import firstproject.firstproject.controller.H2Database;

public class TestH2Database {

    public static void main(String[] args) {
        testInstanceCreation();
    }

    private static boolean testInstanceCreation(){
        H2Database db = H2Database.getInstance();

        if (db == null) {
            System.out.println("Did not get a database instance");
            return false;
        }

        return true;
    }
}
