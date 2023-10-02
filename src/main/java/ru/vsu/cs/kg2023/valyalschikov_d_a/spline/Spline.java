package ru.vsu.cs.kg2023.valyalschikov_d_a.spline;
import org.apache.commons.math3.linear.*;
public class Spline {
    private final PointForSpline firstPoint;
    private final PointForSpline secondPoint;
    private final PointForSpline thirdPoint;
    private final double x1;
    private final double x2;
    private final double x3;
    private final double y1;
    private final double y2;
    private final double y3;
    private double a;
    private double b;
    private double c;
    private double d;
    public Spline(PointForSpline firstPoint, PointForSpline secondPoint, PointForSpline thirdPoint) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.thirdPoint = thirdPoint;
        this.x1 = firstPoint.getX();
        this.x2 = secondPoint.getX();
        this.x3 = thirdPoint.getX();
        this.y1 = firstPoint.getY();
        this.y2 = secondPoint.getY();
        this.y3 = thirdPoint.getY();
        solutionEquation();
        secondPoint.setFirstDiff(3*a * Math.pow(secondPoint.getX(), 2) + 2*b * secondPoint.getX() + c);
    }
    private void solutionEquation(){
        RealMatrix matrix = MatrixUtils.createRealMatrix(new double[][]{
                {Math.pow(x1, 3), Math.pow(x1, 2), x1, 1},
                {Math.pow(x2, 3), Math.pow(x2, 2), x2, 1},
                {Math.pow(x3, 3), Math.pow(x3, 2), x3, 1},
                { 3 * x1*x1, 2 * x1, 1, 0},
        });
        RealVector constants = new ArrayRealVector(
                new double[] {y1, y2, y3, firstPoint.getFirstDiff()});
        LUDecomposition luDecomposition = new LUDecomposition(matrix);
        RealVector solution = luDecomposition.getSolver().solve(constants);
        double[] arrCoeff = solution.toArray();
        this.a =  arrCoeff[0];
        this.b =  arrCoeff[1];
        this.c =  arrCoeff[2];
        this.d =  arrCoeff[3];
    }
    public SetOfPoints getPointsArr(){
        SetOfPoints res = new SetOfPoints();
        double length = thirdPoint.getX() - secondPoint.getX();
        double x = secondPoint.getX();
        for (int i = 0; i < length; i++){
            x++;
            res.addFirstSet(new PointForSpline(x, a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d));
        }
        length = secondPoint.getX() - firstPoint.getX();
        x = firstPoint.getX();
        for (int i = 0; i < length; i++){
            x++;
            res.addSecondSet(new PointForSpline(x, a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d));
        }
        return res;
    }
}