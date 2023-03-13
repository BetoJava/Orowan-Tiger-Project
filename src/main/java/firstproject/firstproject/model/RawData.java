package firstproject.firstproject.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RawData {
    int Lp;
    String MatID;
    double xTime;
    double xLoc;
    double EnThick;
    double ExThick;
    double EnTens;
    double ExTens;
    double RollForce;
    double FSlip;
    double Diameter;
    double RolledLengthForWorkRolls;
    double youngModulus;
    double BackupRollDia;
    double RolledLengthForBackupRolls;
    double mu;
    double torque;
    double averageSigma;
    double inputError;
    double LubWFlUp;
    double LubWFlLo;
    double LubOilFlUp;
    double LubOilFlLo;
    double WorkRollSpeed;

    public RawData() {

    }

    public static ArrayList<RawData> loadRawDataFromFile(String filename) {
        ArrayList<RawData> rawDataList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;

            // br.readLine(); // skip first line (headers)
            while ((line = br.readLine()) != null) {
                String[] values = line.split("; ");
                RawData rawData = new RawData();
                rawData.Lp = Integer.parseInt(values[0]);
                rawData.MatID = values[1];
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
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rawDataList;
    }

    public int getLp() {
        return Lp;
    }

    public void setLp(int lp) {
        Lp = lp;
    }

    public String getMatID() {
        return MatID;
    }

    public void setMatID(String matID) {
        MatID = matID;
    }

    public double getxTime() {
        return xTime;
    }

    public void setxTime(double xTime) {
        this.xTime = xTime;
    }

    public double getxLoc() {
        return xLoc;
    }

    public void setxLoc(double xLoc) {
        this.xLoc = xLoc;
    }

    public double getEnThick() {
        return EnThick;
    }

    public void setEnThick(double enThick) {
        EnThick = enThick;
    }

    public double getExThick() {
        return ExThick;
    }

    public void setExThick(double exThick) {
        ExThick = exThick;
    }

    public double getEnTens() {
        return EnTens;
    }

    public void setEnTens(double enTens) {
        EnTens = enTens;
    }

    public double getExTens() {
        return ExTens;
    }

    public void setExTens(double exTens) {
        ExTens = exTens;
    }

    public double getRollForce() {
        return RollForce;
    }

    public void setRollForce(double rollForce) {
        RollForce = rollForce;
    }

    public double getFSlip() {
        return FSlip;
    }

    public void setFSlip(double FSlip) {
        this.FSlip = FSlip;
    }

    public double getDiameter() {
        return Diameter;
    }

    public void setDiameter(double diameter) {
        Diameter = diameter;
    }

    public double getRolledLengthForWorkRolls() {
        return RolledLengthForWorkRolls;
    }

    public void setRolledLengthForWorkRolls(double rolledLengthForWorkRolls) {
        RolledLengthForWorkRolls = rolledLengthForWorkRolls;
    }

    public double getYoungModulus() {
        return youngModulus;
    }

    public void setYoungModulus(double youngModulus) {
        this.youngModulus = youngModulus;
    }

    public double getBackupRollDia() {
        return BackupRollDia;
    }

    public void setBackupRollDia(double backupRollDia) {
        BackupRollDia = backupRollDia;
    }

    public double getRolledLengthForBackupRolls() {
        return RolledLengthForBackupRolls;
    }

    public void setRolledLengthForBackupRolls(double rolledLengthForBackupRolls) {
        RolledLengthForBackupRolls = rolledLengthForBackupRolls;
    }

    public double getMu() {
        return mu;
    }

    public void setMu(double mu) {
        this.mu = mu;
    }

    public double getTorque() {
        return torque;
    }

    public void setTorque(double torque) {
        this.torque = torque;
    }

    public double getAverageSigma() {
        return averageSigma;
    }

    public void setAverageSigma(double averageSigma) {
        this.averageSigma = averageSigma;
    }

    public double getInputError() {
        return inputError;
    }

    public void setInputError(double inputError) {
        this.inputError = inputError;
    }

    public double getLubWFlUp() {
        return LubWFlUp;
    }

    public void setLubWFlUp(double lubWFlUp) {
        LubWFlUp = lubWFlUp;
    }

    public double getLubWFlLo() {
        return LubWFlLo;
    }

    public void setLubWFlLo(double lubWFlLo) {
        LubWFlLo = lubWFlLo;
    }

    public double getLubOilFlUp() {
        return LubOilFlUp;
    }

    public void setLubOilFlUp(double lubOilFlUp) {
        LubOilFlUp = lubOilFlUp;
    }

    public double getLubOilFlLo() {
        return LubOilFlLo;
    }

    public void setLubOilFlLo(double lubOilFlLo) {
        LubOilFlLo = lubOilFlLo;
    }

    public double getWorkRollSpeed() {
        return WorkRollSpeed;
    }

    public void setWorkRollSpeed(double workRollSpeed) {
        WorkRollSpeed = workRollSpeed;
    }
}