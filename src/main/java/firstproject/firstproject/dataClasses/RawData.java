package firstproject.firstproject.dataClasses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class RawData {

    private int Lp;
    private int MatID;
    private String stand;
    private double xTime;
    private double xLoc;
    private double EnThick;
    private double ExThick;
    private double EnTens;
    private double ExTens;
    private double RollForce;
    private double FSlip;
    private double Diameter;
    private double RolledLengthForWorkRolls;
    private double youngModulus;
    private double BackupRollDia;
    private double RolledLengthForBackupRolls;
    private double mu;
    private double torque;
    private double averageSigma;
    private double inputError;
    private double LubWFlUp;
    private double LubWFlLo;
    private double LubOilFlUp;
    private double LubOilFlLo;
    private double WorkRollSpeed;

    public RawData() {

    }

    public RawData(int lp, int matID, String standId, double xTime, double xLoc, double enThick, double exThick, double enTens, double exTens, double rollForce, double FSlip, double diameter, double rolledLengthForWorkRolls, double youngModulus, double backupRollDia, double rolledLengthForBackupRolls, double mu, double torque, double averageSigma, double inputError, double lubWFlUp, double lubWFlLo, double lubOilFlUp, double lubOilFlLo, double workRollSpeed) {
        Lp = lp;
        MatID = matID;
        this.stand = standId;
        this.xTime = xTime;
        this.xLoc = xLoc;
        EnThick = enThick;
        ExThick = exThick;
        EnTens = enTens;
        ExTens = exTens;
        RollForce = rollForce;
        this.FSlip = FSlip;
        Diameter = diameter;
        RolledLengthForWorkRolls = rolledLengthForWorkRolls;
        this.youngModulus = youngModulus;
        BackupRollDia = backupRollDia;
        RolledLengthForBackupRolls = rolledLengthForBackupRolls;
        this.mu = mu;
        this.torque = torque;
        this.averageSigma = averageSigma;
        this.inputError = inputError;
        LubWFlUp = lubWFlUp;
        LubWFlLo = lubWFlLo;
        LubOilFlUp = lubOilFlUp;
        LubOilFlLo = lubOilFlLo;
        WorkRollSpeed = workRollSpeed;
    }

    /**
     * Renvoie la liste des données brutes présentes dans le fichier donnée en entrée.
     */
    public static ArrayList<RawData> loadRawDataFromFile(String filename) {
        ArrayList<RawData> rawDataList = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line;

            String stand = String.valueOf(filename.charAt(filename.length() - 5));

            while ((line = reader.readLine()) != null) {
                line = line.replace(",", ".");
                String[] values = line.split("; ");
                RawData rawData = new RawData();
                rawData.stand = stand;
                rawData.Lp = Integer.parseInt(values[0]);
                rawData.MatID = Integer.parseInt(values[1]);
                rawData.xTime = Double.parseDouble(values[2]);
                rawData.xLoc = Double.parseDouble(values[3]);
                rawData.EnThick = Double.parseDouble(values[4]);
                rawData.ExThick = Double.parseDouble(values[5]);
                rawData.EnTens = Double.parseDouble(values[6]);
                rawData.ExTens = Double.parseDouble(values[7]);
                rawData.RollForce = Double.parseDouble(values[8]);
                rawData.FSlip = Double.parseDouble(values[9]);
                rawData.Diameter = Double.parseDouble(values[10]);
                rawData.RolledLengthForWorkRolls = Double.parseDouble(values[11]);
                rawData.youngModulus = Double.parseDouble(values[12]);
                rawData.BackupRollDia = Double.parseDouble(values[13]);
                rawData.RolledLengthForBackupRolls = Double.parseDouble(values[14]);
                rawData.mu = Double.parseDouble(values[15]);
                rawData.torque = Double.parseDouble(values[16]);
                rawData.averageSigma = Double.parseDouble(values[17]);
                rawData.inputError = Double.parseDouble(values[18]);
                rawData.LubWFlUp = Double.parseDouble(values[19]);
                rawData.LubWFlLo = Double.parseDouble(values[20]);
                rawData.LubOilFlUp = Double.parseDouble(values[21]);
                rawData.LubOilFlLo = Double.parseDouble(values[22]);
                rawData.WorkRollSpeed = Double.parseDouble(values[23]);
                rawDataList.add(rawData);
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rawDataList;
    }

    public String getStand(){ return this.stand; }

    public int getLp() {
        return Lp;
    }

    public int getMatID() {
        return MatID;
    }

    public double getxTime() {
        return xTime;
    }

    public double getxLoc() {
        return xLoc;
    }

    public double getEnThick() {
        return EnThick;
    }

    public double getExThick() {
        return ExThick;
    }

    public double getEnTens() {
        return EnTens;
    }

    public double getExTens() {
        return ExTens;
    }

    public double getRollForce() {
        return RollForce;
    }

    public double getFSlip() {
        return FSlip;
    }

    public double getDiameter() {
        return Diameter;
    }

    public double getRolledLengthForWorkRolls() {
        return RolledLengthForWorkRolls;
    }

    public double getYoungModulus() {
        return youngModulus;
    }

    public double getBackupRollDia() {
        return BackupRollDia;
    }

    public double getRolledLengthForBackupRolls() {
        return RolledLengthForBackupRolls;
    }

    public double getMu() {
        return mu;
    }

    public double getTorque() {
        return torque;
    }

    public double getAverageSigma() {
        return averageSigma;
    }

    public double getInputError() {
        return inputError;
    }

    public double getLubWFlUp() {
        return LubWFlUp;
    }

    public double getLubWFlLo() {
        return LubWFlLo;
    }

    public double getLubOilFlUp() {
        return LubOilFlUp;
    }

    public double getLubOilFlLo() {
        return LubOilFlLo;
    }

    public double getWorkRollSpeed() {
        return WorkRollSpeed;
    }
}
