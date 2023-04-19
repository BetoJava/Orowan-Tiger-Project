package firstproject.firstproject.dataClasses;

public class OrowanInputData {

    private double rollSpeed;

    private int cas;
    private double He;
    private double Hs;
    private double Te;
    private double Ts;
    private double diam_WR;
    private double WRyoung;
    private double offset_ini;
    private double mu_ini;
    private double Force;
    private double G;

    public OrowanInputData() {

    }


    public int getCas() {
        return cas;
    }

    public void setCas(int cas) {
        this.cas = cas;
    }

    public double getHe() {
        return He;
    }

    public void setHe(double he) {
        He = he;
    }

    public double getHs() {
        return Hs;
    }

    public void setHs(double hs) {
        Hs = hs;
    }

    public double getTe() {
        return Te;
    }

    public void setTe(double te) {
        Te = te;
    }

    public double getTs() {
        return Ts;
    }

    public void setTs(double ts) {
        Ts = ts;
    }

    public double getDiam_WR() {
        return diam_WR;
    }

    public void setDiam_WR(double diam_WR) {
        this.diam_WR = diam_WR;
    }

    public double getWRyoung() {
        return WRyoung;
    }

    public void setWRyoung(double WRyoung) {
        this.WRyoung = WRyoung;
    }

    public double getOffset_ini() {
        return offset_ini;
    }

    public void setOffset_ini(double offset_ini) {
        this.offset_ini = offset_ini;
    }

    public double getMu_ini() {
        return mu_ini;
    }

    public void setMu_ini(double mu_ini) {
        this.mu_ini = mu_ini;
    }

    public double getForce() {
        return Force;
    }

    public void setForce(double force) {
        Force = force;
    }

    public double getG() {
        return G;
    }

    public void setG(double g) {
        G = g;
    }

    public double getRollSpeed() {
        return rollSpeed;
    }

    public void setRollSpeed(double rollSpeed) {
        this.rollSpeed = rollSpeed;
    }
}
