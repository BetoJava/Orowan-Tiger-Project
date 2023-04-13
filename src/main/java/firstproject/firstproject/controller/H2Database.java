package firstproject.firstproject.controller;

import java.sql.*;
import java.util.ArrayList;

import firstproject.firstproject.model.*;

public class H2Database {

    private static H2Database instance = null;

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static String connectionPath = "jdbc:h2:file:~/default";
    private static String dbUsername = "";
    private static String dbPassword = "";
    private static Connection connection;

    private static final User thisUser = new User();

    public static H2Database getInstance(){
        if(instance == null)
            H2Database.instance = new H2Database("test", "sa", "");//"TigerDatabase", "sherekhan", "grrr");

        return instance;
    }

    private H2Database(String dbName, String username, String password){
        //Set up database object
        H2Database.connectionPath = "jdbc:h2:tcp://localhost/~/" + dbName;
        H2Database.dbUsername = username;
        H2Database.dbPassword = password;

        try {
            Class.forName("org.h2.Driver");

            H2Database.connection = DriverManager.getConnection(H2Database.connectionPath, H2Database.dbUsername, H2Database.dbPassword);
            setUpDatabase();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpDatabase() throws SQLException{

        Statement stmt = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS sensor_data " +
                "(stand_id VARCHAR(255) NOT NULL," +
                " lp INT NOT NULL, " +
                " mat_id INT NOT NULL, " +
                " x_time FLOAT NOT NULL, " +
                " x_loc FLOAT NOT NULL, " +
                " en_thick FLOAT NOT NULL, " +
                " ex_thick FLOAT NOT NULL, " +
                " en_tens FLOAT NOT NULL, " +
                " ex_tens FLOAT NOT NULL, " +
                " roll_force FLOAT NOT NULL, " +
                " f_slip FLOAT NOT NULL, " +
                " diameter FLOAT NOT NULL, " +
                " rolled_length_wr FLOAT NOT NULL, " +
                " young_modulus FLOAT NOT NULL, " +
                " backup_roll_dia FLOAT NOT NULL, " +
                " rolled_length_br FLOAT NOT NULL, " +
                " mu FLOAT NOT NULL, " +
                " torque FLOAT NOT NULL, " +
                " avg_sigma FLOAT NOT NULL, " +
                " input_error FLOAT NOT NULL, " +
                " lub_wfl_up FLOAT NOT NULL, " +
                " lub_wfl_lo FLOAT NOT NULL, " +
                " lub_oil_fl_up FLOAT NOT NULL, " +
                " lub_oil_fl_lo FLOAT NOT NULL, " +
                " wr_speed FLOAT NOT NULL)";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS orowan_data " +
                "(stand_id VARCHAR(255) NOT NULL," +
                " mat_id INT NOT NULL, " +
                " case_id INT NOT NULL, " +
                " errors VARCHAR(255) NOT NULL, " +
                " offset_yield FLOAT NOT NULL, " +
                " friction FLOAT NOT NULL, " +
                " rolling_torque FLOAT NOT NULL, " +
                " sigma_moy FLOAT NOT NULL, " +
                " sigma_ini FLOAT NOT NULL, " +
                " sigma_out FLOAT NOT NULL, " +
                " sigma_max FLOAT NOT NULL, " +
                " force_error_pct FLOAT NOT NULL, " +
                " slip_error_pct FLOAT NOT NULL, " +
                " has_converged BOOLEAN NOT NULL)";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS processed_data" +
                " (stand_id VARCHAR(255) NOT NULL," +
                "  mat_id INT NOT NULL," +
                "  friction FLOAT NOT NULL," +
                "  sigma FLOAT NOT NULL," +
                "  rollingSpeed FLOAT NOT NULL)";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id INT AUTO_INCREMENT PRIMARY KEY," +
                " username VARCHAR(255) NOT NULL, " +
                " password VARCHAR(255) NOT NULL, " +
                " isEngineer BOOLEAN NOT NULL)";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS strip_stand (" +
                "stand_id VARCHAR(255)," +
                "mat_id INT PRIMARY KEY)";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS users_stands " +
                "(user_id INT AUTO_INCREMENT PRIMARY KEY," +
                " stand_id VARCHAR(255) NOT NULL)";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS stands (" +
                "stand_id VARCHAR(255) PRIMARY KEY, " +
                "enabled BOOLEAN NOT NULL)";
        stmt.executeUpdate(sql);

        stmt.close();
    }

    //---------------------------DATA MANAGEMENT--------------------------------

    public ArrayList<OrowanOutputData> loadOrowanData(int stripID, String stand) {
        ArrayList<OrowanOutputData> data = new ArrayList<>();

        //Check access rights
        if(!isUserEngineer() && !H2Database.thisUser.hasStand(stand))
            return data;

        String query = "SELECT * FROM orowan_data WHERE stand_id = ? AND mat_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stand);
            pstmt.setInt(2, stripID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int matId = rs.getInt("mat_id");
                int caseId = rs.getInt("case_id");
                String errors = rs.getString("errors");
                double offsetYield = rs.getDouble("offset_yield");
                double friction = rs.getDouble("friction");
                double rollingTorque = rs.getDouble("rolling_torque");
                double sigmaMoy = rs.getDouble("sigma_moy");
                double sigmaIni = rs.getDouble("sigma_ini");
                double sigmaOut = rs.getDouble("sigma_out");
                double sigmaMax = rs.getDouble("sigma_max");
                double forceError = rs.getDouble("force_error_pct");
                double slipError = rs.getDouble("slip_error_pct");
                boolean hasConverged = rs.getBoolean("has_converged");

                OrowanOutputData row = new OrowanOutputData(stand, matId, caseId, errors, offsetYield, friction, rollingTorque, sigmaMoy, sigmaIni,
                        sigmaOut, sigmaMax, forceError, slipError, hasConverged);
                data.add(row);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return data;
    }

    public ArrayList<RawData> loadRawData(int stripID, String stand) {
        ArrayList<RawData> data = new ArrayList<>();

        //Check access rights
        if(!isUserEngineer() && !H2Database.thisUser.hasStand(stand))
            return data;

        String query = "SELECT * FROM sensor_data WHERE stand_id = ? AND mat_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stand);
            pstmt.setInt(2, stripID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int matId = rs.getInt("mat_id");
                int lp = rs.getInt("lp");
                float xTime = rs.getFloat("x_time");
                float xLoc = rs.getFloat("x_loc");
                float enThick = rs.getFloat("en_thick");
                float exThick = rs.getFloat("ex_thick");
                float enTens = rs.getFloat("en_tens");
                float exTens = rs.getFloat("ex_tens");
                float rollForce = rs.getFloat("roll_force");
                float fSlip = rs.getFloat("f_slip");
                float diameter = rs.getFloat("diameter");
                float rolledLengthWr = rs.getFloat("rolled_length_wr");
                float youngModulus = rs.getFloat("young_modulus");
                float backupRollDia = rs.getFloat("backup_roll_dia");
                float rolledLengthBr = rs.getFloat("rolled_length_br");
                float mu = rs.getFloat("mu");
                float torque = rs.getFloat("torque");
                float avgSigma = rs.getFloat("avg_sigma");
                float inputError = rs.getFloat("input_error");
                float lubWflUp = rs.getFloat("lub_wfl_up");
                float lubWflLo = rs.getFloat("lub_wfl_lo");
                float lubOilFlUp = rs.getFloat("lub_oil_fl_up");
                float lubOilFlLo = rs.getFloat("lub_oil_fl_lo");
                float wrSpeed = rs.getFloat("wr_speed");

                RawData row = new RawData(lp, matId, stand, xTime, xLoc, enThick, exThick, enTens, exTens, rollForce, fSlip, diameter,
                        rolledLengthWr, youngModulus, backupRollDia, rolledLengthBr, mu, torque, avgSigma, inputError, lubWflUp,
                        lubWflLo, lubOilFlUp, lubOilFlLo, wrSpeed);
                data.add(row);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return data;
    }

    public ArrayList<ProcessedOutputData> loadProcessedOutputData(int stripID, String stand) {
        ArrayList<ProcessedOutputData> data = new ArrayList<>();

        //Check access rights
        if(!isUserEngineer() && !H2Database.thisUser.hasStand(stand))
            return data;

        String query = "SELECT * FROM processed_data WHERE stand_id = ? AND mat_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stand);
            pstmt.setInt(2, stripID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                double friction = rs.getDouble("friction");
                double sigma = rs.getDouble("sigma");
                double rollingSpeed = rs.getDouble("rollingSpeed");

                ProcessedOutputData row = new ProcessedOutputData(rollingSpeed, sigma, friction , stand, stripID);
                data.add(row);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return data;
    }

    public void writeOrowanData(ArrayList<OrowanOutputData> data){

        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO orowan_data (stand_id, mat_id, case_id, errors, offset_yield, friction, rolling_torque, sigma_moy, sigma_ini, sigma_out, sigma_max, force_error_pct, slip_error_pct, has_converged) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (OrowanOutputData row : data) {
                stmt.setString(1, row.getStandID());
                stmt.setInt(2, row.getMatID());
                stmt.setInt(3, row.getCaseId());
                stmt.setString(4, row.getErrors());
                stmt.setDouble(5, row.getOffsetYield());
                stmt.setDouble(6, row.getFriction());
                stmt.setDouble(7, row.getRollingTorque());
                stmt.setDouble(8, row.getSigmaMoy());
                stmt.setDouble(9, row.getSigmaIni());
                stmt.setDouble(10, row.getSigmaOut());
                stmt.setDouble(11, row.getSigmaMax());
                stmt.setDouble(12, row.getForceError());
                stmt.setDouble(13, row.getSlipError());
                stmt.setBoolean(14, row.isHasConverged());
                stmt.executeUpdate();
            }

            stmt.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void writeSensorData(ArrayList<RawData> dataList){

        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO sensor_data " +
                "(lp, mat_id, x_time, x_loc, en_thick, ex_thick, en_tens, ex_tens, roll_force, f_slip, diameter, " +
                "rolled_length_wr, young_modulus, backup_roll_dia, rolled_length_br, mu, torque, avg_sigma, " +
                "input_error, lub_wfl_up, lub_wfl_lo, lub_oil_fl_up, lub_oil_fl_lo, wr_speed, stand_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            for (RawData data : dataList) {
                stmt.setInt(1, data.getLp());
                stmt.setInt(2, data.getMatID());
                stmt.setDouble(3, data.getxTime()); // assuming xTime is in seconds
                stmt.setDouble(4, data.getxLoc());
                stmt.setDouble(5, data.getEnThick());
                stmt.setDouble(6, data.getExThick());
                stmt.setDouble(7, data.getEnTens());
                stmt.setDouble(8, data.getExTens());
                stmt.setDouble(9, data.getRollForce());
                stmt.setDouble(10, data.getFSlip());
                stmt.setDouble(11, data.getDiameter());
                stmt.setDouble(12, data.getRolledLengthForWorkRolls());
                stmt.setDouble(13, data.getYoungModulus());
                stmt.setDouble(14, data.getBackupRollDia());
                stmt.setDouble(15, data.getRolledLengthForBackupRolls());
                stmt.setDouble(16, data.getMu());
                stmt.setDouble(17, data.getTorque());
                stmt.setDouble(18, data.getAverageSigma());
                stmt.setDouble(19, data.getInputError());
                stmt.setDouble(20, data.getLubWFlUp());
                stmt.setDouble(21, data.getLubWFlLo());
                stmt.setDouble(22, data.getLubOilFlUp());
                stmt.setDouble(23, data.getLubOilFlLo());
                stmt.setDouble(24, data.getWorkRollSpeed());
                stmt.setString(25, data.getStand());
                stmt.executeUpdate();
            }

            stmt.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void writeProcessedOutputData(ArrayList<ProcessedOutputData> data){

        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO processed_data (stand_id, mat_id, friction, sigma, rollingSpeed) " +
                    "VALUES (?, ?, ?, ?, ?)");

            for (ProcessedOutputData row : data) {
                stmt.setString(1, row.getStand());
                stmt.setInt(2, row.getStripID());
                stmt.setDouble(3, row.getFriction());
                stmt.setDouble(4, row.getSigma());
                stmt.setDouble(5, row.getRollingSpeed());
                stmt.executeUpdate();
            }
            stmt.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //----------------------------USER MANAGEMENT----------------------------------
    public ArrayList<Stand> getUserStands(){
        return H2Database.thisUser.getStandList();
    }

    public static boolean isUserEngineer(){ return H2Database.thisUser.isEngineer(); }

    public boolean loginUser(String username, String password){
        boolean loginSuccessful = false;

        try {
            String sql = "SELECT id, isEngineer FROM users WHERE username=? AND password=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                H2Database.thisUser.setIdentifier(username);
                H2Database.setUserIsEngineer(rs.getBoolean("isEngineer"));
                H2Database.thisUser.setId( rs.getInt("id") );
                refreshUserStands();

                loginSuccessful = true;
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loginSuccessful;
    }

    public void refreshUserStands(){ H2Database.thisUser.setStandList(loadUserStands()); }

    private ArrayList<Stand> loadUserStands(){

        ArrayList<Stand> stands;

        if (H2Database.isUserEngineer()) {
            stands = getAllStands();
        } else {
            stands = getUserStands(H2Database.thisUser.getId());
        }

        return stands;
    }

    public void addUser(String username, String password, boolean isEngineer) {
        if (!H2Database.isUserEngineer())
            return;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (username, password, isEngineer) VALUES (?, ?, ?)"
            );
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setBoolean(3, isEngineer);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(int userId) {
        if (!H2Database.isUserEngineer())
            return;

        try {
            PreparedStatement deleteStandsStatement = connection.prepareStatement(
                    "DELETE FROM users_stands WHERE user_id = ?"
            );
            deleteStandsStatement.setInt(1, userId);
            deleteStandsStatement.executeUpdate();
            deleteStandsStatement.close();

            PreparedStatement deleteUserStatement = connection.prepareStatement(
                    "DELETE FROM users WHERE id = ?"
            );
            deleteUserStatement.setInt(1, userId);
            deleteUserStatement.executeUpdate();
            deleteUserStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStandForUser(int userId, String standId) {
        if (!H2Database.isUserEngineer())
            return;

        try {
            PreparedStatement checkStatement = connection.prepareStatement(
                    "SELECT user_id FROM users_stands WHERE user_id = ? AND stand_id = ?"
            );
            checkStatement.setInt(1, userId);
            checkStatement.setString(2, standId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (!resultSet.next()) {
                PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT INTO users_stands (user_id, stand_id) VALUES (?, ?)"
                );
                insertStatement.setInt(1, userId);
                insertStatement.setString(2, standId);
                insertStatement.executeUpdate();
                insertStatement.close();
            }

            checkStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeStandForUser(int userId, String standId) {
        if (!H2Database.isUserEngineer())
            return;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users_stands WHERE user_id = ? AND stand_id = ?"
            );
            statement.setInt(1, userId);
            statement.setString(2, standId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add_strip(int mat, String stand){
        try {
            String insertSql = "INSERT INTO strip_stand (mat_id, stand_id) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSql);
            pstmt.setInt(1, mat);
            pstmt.setString(2, stand);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStand(String stand_id) {
        String sql = "INSERT INTO stands (stand_id, enabled) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, stand_id);
            pstmt.setBoolean(2, true);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Enable a stand
    public void enableStand(String stand_id){
        String sql = "UPDATE stands SET enabled = true WHERE stand_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, stand_id);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Disable a stand
    public void disableStand(String stand_id) {
        String sql = "UPDATE stands SET enabled = false WHERE stand_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, stand_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();

        if (!isUserEngineer())
            return users;

        String sql = "SELECT id, username, password, isEngineer FROM users";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getBoolean("isEngineer") ? User.ENGINEER : User.WORKER;
                User user = new User(id, username, password, role);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<Stand> getAllStands(){
        ArrayList<Stand> stands = new ArrayList<>();

        if(!isUserEngineer())
            return stands;

        String sql = "SELECT stand_id, enabled FROM stands";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            getStands_aux(pstmt, stands);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stands;
    }

    public ArrayList<Stand> getUserStands(int userID){
        ArrayList<Stand> stands = new ArrayList<>();

        if(!isUserEngineer() && !(H2Database.thisUser.getId() == userID))
            return stands;

        String sql = "SELECT stand_id, enabled FROM stands " +
                "WHERE stand_id in " +
                "(SELECT stand_id FROM users_stands" +
                "WHERE user_id = ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, userID);
            getStands_aux(pstmt, stands);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stands;
    }

    private ArrayList<Stand> getStands_aux(PreparedStatement pstmt, ArrayList<Stand> stands) throws SQLException{
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String stand_id = rs.getString("stand_id");
            Boolean enabled = rs.getBoolean("enabled");
            ArrayList<Strip> strips = getStrips(stand_id);
            stands.add(new Stand(stand_id, strips, enabled));
        }

        return stands;
    }

    private ArrayList<Strip> getStrips(String standId){
        ArrayList<Strip> strips = new ArrayList<>();

        try {
            String selectSql = "SELECT mat_id FROM strip_stand WHERE stand_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, standId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                strips.add(new Strip(rs.getInt("mat_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return strips;
    }

    public static void setUserIsEngineer(boolean userIsEngineer) { H2Database.thisUser.setRole( userIsEngineer ? User.ENGINEER : User.WORKER); }
}
