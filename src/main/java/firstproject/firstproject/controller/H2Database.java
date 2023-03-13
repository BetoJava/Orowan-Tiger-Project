package firstproject.firstproject.controller;

import java.sql.*;
import java.util.ArrayList;

public class H2Database {

    private static H2Database instance = null;

    private static String connectionPath = "jdbc:h2:file:~/default";
    private static String dbUsername = "";
    private static String dbPassword = "";

    private static ArrayList<ResultSet> openedResultSets;

    public static H2Database getInstance(){
        if(instance == null)
            H2Database.instance = new H2Database("tigerDatabase", "", "");

        return instance;
    }

    private H2Database(String dbName, String username, String password){
        //Set up database object
        H2Database.connectionPath = "jdbc:h2:file:~/" + dbName;
        H2Database.dbUsername = username;
        H2Database.dbPassword = password;

        //Create appropriate tables

    }

    //Executes query then apply a function
    private void executeQuery(String query) throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:h2:file:/path/to/database", "username", "password");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM my_table");

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.println(id + " " + name);
        }

        rs.close();
        stmt.close();
        conn.close();
    }

}
