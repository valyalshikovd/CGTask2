package ru.vsu.cs.kg2023.valyalschikov_d_a.spline;
import java.util.ArrayList;
import java.util.List;

public class Line {
    private final ArrayList<PointForSpline> pointsX = new ArrayList<PointForSpline>();
    private final ArrayList<PointForSpline> pointsY = new ArrayList<PointForSpline>();
    private int counterOfPoints = 0;
    private final double coefficient;
    public ArrayList<PointForSpline> getPointsX() {
        return pointsX;
    }
    public ArrayList<PointForSpline> getPointsY() {
        return pointsY;
    }
    public int getCounterOfPoints() {
        return counterOfPoints;
    }
    public Line(double coefficient) {
        this.coefficient = coefficient;
    }
    public void addPointToLine(double x, double y){
        if(counterOfPoints == 0){
            pointsX.add(new PointForSpline(0, x));
            pointsY.add(new PointForSpline(0, y));
            counterOfPoints++;
            return;
        }
        if (counterOfPoints == 1) {
            double params = pointsX.get(0).getCoordParameter() + coefficient * Math.pow(Math.pow(x- pointsX.get(counterOfPoints - 1).getVal(), 2) +
                    Math.pow(y - pointsY.get(counterOfPoints - 1).getVal(), 2), 0.5);
            PointForSpline ptX = new PointForSpline(params, x);
            PointForSpline ptY = new PointForSpline(params, y);
            ptX.setFirstDiff((ptX.getVal() - pointsX.get(0).getVal()) /
                    (ptX.getCoordParameter() - pointsX.get(0).getCoordParameter()));
            ptY.setFirstDiff((ptY.getVal() - pointsY.get(0).getVal()) /
                    (ptY.getCoordParameter() - pointsY.get(0).getCoordParameter()));
            pointsX.add(ptX);
            pointsY.add(ptY);
            counterOfPoints++;
            return;
        }
        double params = pointsX.get(counterOfPoints-1).getCoordParameter() + coefficient * Math.pow(Math.pow(x - pointsX.get(counterOfPoints - 1).getVal(), 2) +
                Math.pow(y - pointsY.get(counterOfPoints - 1).getVal(), 2), 0.5);
        PointForSpline ptX = new PointForSpline(params, x);
        PointForSpline ptY = new PointForSpline(params, y);
        ptX.setFirstDiff((ptX.getVal() - pointsX.get(counterOfPoints-2).getVal()) /
                (ptX.getCoordParameter() - pointsX.get(counterOfPoints-2).getCoordParameter()));
        ptY.setFirstDiff((ptY.getVal() - pointsY.get(counterOfPoints-2).getVal()) /
                (ptY.getCoordParameter() - pointsY.get(counterOfPoints-2).getCoordParameter()));
        pointsX.add(ptX);
        pointsY.add(ptY);
        counterOfPoints++;
    }
    public List<Double> getXPoints(){
        return new Spline(pointsX).getSpline();
    }
    public List<Double> getYPoints(){
        return new Spline(pointsY).getSpline();
    }
}
