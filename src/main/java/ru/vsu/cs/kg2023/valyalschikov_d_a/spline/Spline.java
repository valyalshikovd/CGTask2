package ru.vsu.cs.kg2023.valyalschikov_d_a.spline;

import org.apache.commons.math3.linear.*;

import java.util.ArrayList;
import java.util.List;

public class Spline {
    private List<PointForSpline> val;
    private List<Double> spline = new ArrayList<Double>();

    public List<Double> getSpline() {
        return spline;
    }
public int c = 1;
    public boolean isX;

    public Spline(List<PointForSpline> val, boolean isX) {
        this.val = val;
        this.isX = isX;

        getResultats();

    }
    private void getResultats() {
        for (int i = 2; i < val.size(); i++) {
            double[] coefficients = solutionEquation(val.get(i-2), val.get(i-1), val.get(i));

    //        System.out.println(val.get(i - 1).getCoordParameter() + " Параметр точки -1");
     //       System.out.println(val.get(i - 2).getCoordParameter() + " Параметр точки -2");
            double length = val.get(i - 1).getCoordParameter() - val.get(i - 2).getCoordParameter();
            double x = val.get(i - 2).getCoordParameter();
            //System.out.println(length + "длинна");
            //System.out.println(val.get(i - 1).getCoordParameter());
            for (int j = 0; j < Math.abs(length); j++) {
              //  System.out.println(length);
         //       System.out.println(j);
                x ++;
               // System.out.println(x);
                spline.add(coefficients[0] * Math.pow(x, 3) +
                        coefficients[1] * Math.pow(x, 2) +
                                coefficients[2] * x+
                        coefficients[3]
                        );
            }
            if(i == val.size() - 1){
                length = val.get(i ).getCoordParameter() - val.get(i -1).getCoordParameter();
                x = val.get(i - 1).getCoordParameter();
                for (int k = 0; k < Math.abs(length) ; k++) {
                    x++;
                    spline.add( coefficients[0] * Math.pow(x, 3) +
                            coefficients[1] * Math.pow(x, 2)+
                            coefficients[2] * x  +
                            coefficients[3]
                    );
                }
            }

        }

    }

    private double[] solutionEquation(PointForSpline pt1, PointForSpline pt2, PointForSpline pt3) {
        double x1 = pt1.getCoordParameter();
        double x2 = pt2.getCoordParameter();
        double x3 = pt3.getCoordParameter();




        RealMatrix matrix = MatrixUtils.createRealMatrix(new double[][]{
                {Math.pow(x1, 3), Math.pow(x1, 2), x1, 1},
                {Math.pow(x2, 3), Math.pow(x2, 2), x2, 1},
                {Math.pow(x3, 3), Math.pow(x3, 2), x3, 1},
                {3 * x1 * x1, 2 * x1, 1, 0},
            //    {6 * x1, 4, 0, 0}
        });
        RealVector constants = new ArrayRealVector(
                new double[]{pt1.getVal() , pt2.getVal() , pt3.getVal() , pt1.getFirstDiff()});
        LUDecomposition luDecomposition = new LUDecomposition(matrix);
        RealVector solution = luDecomposition.getSolver().solve(constants);
        double[] res = solution.toArray();
        pt2.setFirstDiff(
                3 * x2 * x2 * res[0] + 2 * x2  * res[1] + res[2]
        );
        pt2.setSecondDiff(
                6 * x2  * res[0] + 4  * res[1]
        );


//          System.out.println(
//                + x1 + " параметр 1" + "\n"
//                + x2 + " параметр 2" + "\n"
//                + x3 + " параметр 3" + "\n"
//                + pt1.getVal() + " значение 1" + "\n"
//                + pt2.getVal() + " значение 2" + "\n"
//                + pt3.getVal() + " значение 3" + "\n"
//                + res[0] + " коэффициент А" + "\n"
//                + res[1] + " коэффициент В" + "\n"
//                + res[2] + " коэффициент С" + "\n"
//                + res[3] + " коэффициент D" + "\n"
//        + "Это x " + isX);

        return solution.toArray();

    }
}