package ru.vsu.cs.kg2023.valyalschikov_d_a.spline;

public class PointForSpline  {
    private double firstDiff;

    public double getSecondDiff() {
        return secondDiff;
    }

    public void setSecondDiff(double secondDiff) {
        this.secondDiff = secondDiff;
    }

    private double secondDiff;
    private double param;
    private double val;

    public double getCoordParameter() {
        return param;
    }

    public double getVal() {
        return val;
    }

    public void setCoordParameter(double coordParameter) {
        this.param = coordParameter;
    }

    public double getFirstDiff() {
        return firstDiff;
    }
    public void setFirstDiff(double firstDiff) {
        this.firstDiff = firstDiff;
    }
    public PointForSpline(double param, double val) {
        this.param = param;
        this.val = val;
        firstDiff = 0;
    }
    @Override
    public String toString() {
        return "PointForSpline{" + super.toString() +
                "firstDiff=" + firstDiff;
    }
}
