package firstproject.firstproject.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OrowanOutputData {

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

    public OrowanOutputData(int caseId, String errors, double offsetYield, double friction, double rollingTorque, double sigmaMoy,
                            double sigmaIni, double sigmaOut, double sigmaMax, double forceError, double slipError, boolean hasConverged) {
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

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public double getOffsetYield() {
        return offsetYield;
    }

    public void setOffsetYield(double offsetYield) {
        this.offsetYield = offsetYield;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public double getRollingTorque() {
        return rollingTorque;
    }

    public void setRollingTorque(double rollingTorque) {
        this.rollingTorque = rollingTorque;
    }

    public double getSigmaMoy() {
        return sigmaMoy;
    }

    public void setSigmaMoy(double sigmaMoy) {
        this.sigmaMoy = sigmaMoy;
    }

    public double getSigmaIni() {
        return sigmaIni;
    }

    public void setSigmaIni(double sigmaIni) {
        this.sigmaIni = sigmaIni;
    }

    public double getSigmaOut() {
        return sigmaOut;
    }

    public void setSigmaOut(double sigmaOut) {
        this.sigmaOut = sigmaOut;
    }

    public double getSigmaMax() {
        return sigmaMax;
    }

    public void setSigmaMax(double sigmaMax) {
        this.sigmaMax = sigmaMax;
    }

    public double getForceError() {
        return forceError;
    }

    public void setForceError(double forceError) {
        this.forceError = forceError;
    }

    public double getSlipError() {
        return slipError;
    }

    public void setSlipError(double slipError) {
        this.slipError = slipError;
    }

    public boolean isHasConverged() {
        return hasConverged;
    }

    public void setHasConverged(boolean hasConverged) {
        this.hasConverged = hasConverged;
    }
}
