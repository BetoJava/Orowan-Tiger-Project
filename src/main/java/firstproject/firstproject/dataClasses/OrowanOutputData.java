package firstproject.firstproject.dataClasses;

public class OrowanOutputData {

    private double rollSpeed;

    private String stand;
    private int matId;
    private int caseId;
    private String errors;
    private double offsetYield;
    private double friction;
    private double rollingTorque;
    private double sigmaMoy;
    private double sigmaIni;
    private double sigmaOut;
    private double sigmaMax;
    private double forceError;
    private double slipError;
    private boolean hasConverged;

    public OrowanOutputData() {

    }

    public OrowanOutputData(String stand, int matId, int caseId, String errors, double offsetYield, double friction, double rollingTorque, double sigmaMoy,
                            double sigmaIni, double sigmaOut, double sigmaMax, double forceError, double slipError, boolean hasConverged) {
        this.stand = stand;
        this.matId = matId;
        this.caseId = caseId;
        this.errors = errors;
        this.offsetYield = offsetYield;
        this.friction = friction;
        this.rollingTorque = rollingTorque;
        this.sigmaMoy = sigmaMoy;
        this.sigmaIni = sigmaIni;
        this.sigmaOut = sigmaOut;
        this.sigmaMax = sigmaMax;
        this.forceError = forceError;
        this.slipError = slipError;
        this.hasConverged = hasConverged;
    }

    public String getStandID(){ return stand; }

    public int getCaseId() {
        return caseId;
    }

    public String getErrors() {
        return errors;
    }

    public double getOffsetYield() {
        return offsetYield;
    }

    public double getFriction() {
        return friction;
    }

    public double getRollingTorque() {
        return rollingTorque;
    }

    public double getSigmaMoy() {
        return sigmaMoy;
    }

    public double getSigmaIni() {
        return sigmaIni;
    }

    public double getSigmaOut() {
        return sigmaOut;
    }

    public double getSigmaMax() {
        return sigmaMax;
    }

    public double getForceError() {
        return forceError;
    }

    public double getSlipError() {
        return slipError;
    }

    public boolean isHasConverged() {
        return hasConverged;
    }

    public int getMatID() {
        return matId;
    }

    public double getRollSpeed() {
        return rollSpeed;
    }

    public void setRollSpeed(double rollSpeed) {
        this.rollSpeed = rollSpeed;
    }
}
