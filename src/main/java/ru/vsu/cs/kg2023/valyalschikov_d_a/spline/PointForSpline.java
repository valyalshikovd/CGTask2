package ru.vsu.cs.kg2023.valyalschikov_d_a.spline;
import javafx.geometry.Point2D;
public class PointForSpline extends Point2D {
    private double firstDiff;
    public double getFirstDiff() {
        return firstDiff;
    }
    public void setFirstDiff(double firstDiff) {
        this.firstDiff = firstDiff;
    }
    public PointForSpline(double var1, double var2) {
        super(var1,  var2);
        firstDiff = 0;
    }
    @Override
    public String toString() {
        return "PointForSpline{" + super.toString() +
                "firstDiff=" + firstDiff;
    }
}
