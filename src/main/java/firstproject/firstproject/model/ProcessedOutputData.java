package firstproject.firstproject.model;

public class ProcessedOutputData {

    public static final float xTimeMS = 0.2f;

    private double rollingSpeed;
    private double sigma;
    private double friction;
    private String stand;
    private String stripID;

    public ProcessedOutputData(double rollingSpeed, double sigma, double friction, String stand, String stripID) {
        this.rollingSpeed = rollingSpeed;
        this.sigma = sigma;
        this.friction = friction;
        this.stand = stand;
        this.stripID = stripID;
    }

    public double getFriction() {
        return friction;
    }

    public double getRollingSpeed() {
        return rollingSpeed;
    }

    public double getSigma() {
        return sigma;
    }

    public String getStand() {
        return stand;
    }

    public String getStripID() {
        return stripID;
    }
}
