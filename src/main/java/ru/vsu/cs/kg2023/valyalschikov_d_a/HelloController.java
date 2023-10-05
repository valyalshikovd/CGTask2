package ru.vsu.cs.kg2023.valyalschikov_d_a;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import ru.vsu.cs.kg2023.valyalschikov_d_a.spline.PointForSpline;
import ru.vsu.cs.kg2023.valyalschikov_d_a.spline.Spline;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    ArrayList<PointForSpline> pointsX = new ArrayList<PointForSpline>();
    ArrayList<PointForSpline> pointsY = new ArrayList<PointForSpline>();
    List<PointForSpline> lastPoints = new ArrayList<>();
    ArrayList<Double> diffParams = new ArrayList<>();
    int counterOfPoints = 0;
    double c = 0.2;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        canvas.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case PRIMARY -> handlePrimaryClick(canvas.getGraphicsContext2D(), event);
            }
        });
    }

    private void handlePrimaryClick(GraphicsContext graphicsContext, MouseEvent event) {
        final Point2D clickPoint = new Point2D(event.getX(), event.getY());

        if(counterOfPoints == 0){
            System.out.println("-");
            pointsX.add(new PointForSpline(0, clickPoint.getX()));
            pointsY.add(new PointForSpline(0, clickPoint.getY()));
            counterOfPoints++;
            drawSpline(graphicsContext);
            return;
        }

        if (counterOfPoints == 1) {
            System.out.println("--");
            double params = pointsX.get(0).getCoordParameter() + c * Math.pow(Math.pow(clickPoint.getX() - pointsX.get(counterOfPoints - 1).getVal(), 2) +
                    Math.pow(clickPoint.getY() - pointsY.get(counterOfPoints - 1).getVal(), 2), 0.5);
            diffParams.add(c*Math.pow(Math.pow(clickPoint.getX() - pointsX.get(counterOfPoints - 1).getVal(), 2) +
                    Math.pow(clickPoint.getY() - pointsY.get(counterOfPoints - 1).getVal(), 2), 0.5));
            System.out.println(params + " ПАРМЕТР ");
            PointForSpline ptX = new PointForSpline(params, clickPoint.getX());
            PointForSpline ptY = new PointForSpline(params, clickPoint.getY());
            ptX.setFirstDiff((ptX.getVal() - pointsX.get(0).getVal()) /
                    (ptX.getCoordParameter() - pointsX.get(0).getCoordParameter()));
            ptY.setFirstDiff((ptY.getVal() - pointsY.get(0).getVal()) /
                    (ptY.getCoordParameter() - pointsY.get(0).getCoordParameter()));
            ptX.setSecondDiff(0);
            ptY.setSecondDiff(0);
            pointsX.add(ptX);
            pointsY.add(ptY);
            counterOfPoints++;
            drawSpline(graphicsContext);
            return;
        }
        double params = pointsX.get(counterOfPoints-1).getCoordParameter() + c * Math.pow(Math.pow(clickPoint.getX() - pointsX.get(counterOfPoints - 1).getVal(), 2) +
                Math.pow(clickPoint.getY() - pointsY.get(counterOfPoints - 1).getVal(), 2), 0.5);
       // System.out.println(params  + " ПАРМЕТР ");

        diffParams.add(c*Math.pow(Math.pow(clickPoint.getX() - pointsX.get(counterOfPoints - 1).getVal(), 2) +
                Math.pow(clickPoint.getY() - pointsY.get(counterOfPoints - 1).getVal(), 2), 0.5));
        //System.out.println(Math.pow(Math.pow(clickPoint.getX() - pointsX.get(counterOfPoints - 1).getVal(), 2) +
      //          Math.pow(clickPoint.getY() - pointsY.get(counterOfPoints - 1).getVal(), 2), 0.5) + "добавка");
        PointForSpline ptX = new PointForSpline(params, clickPoint.getX());
        PointForSpline ptY = new PointForSpline(params, clickPoint.getY());
        ptX.setFirstDiff((ptX.getVal() - pointsX.get(counterOfPoints-2).getVal()) /
                (ptX.getCoordParameter() - pointsX.get(counterOfPoints-2).getCoordParameter()));
        ptY.setFirstDiff((ptY.getVal() - pointsY.get(counterOfPoints-2).getVal()) /
                (ptY.getCoordParameter() - pointsY.get(counterOfPoints-2).getCoordParameter()));
        pointsX.add(ptX);
        pointsY.add(ptY);

        counterOfPoints++;
//        System.out.println(Arrays.toString(pointsX.toArray()));
//        System.out.println(Arrays.toString(pointsY.toArray()));
        System.out.println("---");
        drawSpline(graphicsContext);

    }

    private void drawSpline(GraphicsContext graphicsContext){
        for (PointForSpline pt : pointsX){
            System.out.println(pt.getCoordParameter());
        }
        System.out.println("--diffs--");
        for (Double pt: diffParams){
            System.out.println(pt);
        }
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        final int POINT_RADIUS = 3;
        for(int i = 0; i < counterOfPoints; i++){
            graphicsContext.fillOval(
                    pointsX.get(i).getVal() - POINT_RADIUS, pointsY.get(i).getVal(),
                    2 * POINT_RADIUS, 2 * POINT_RADIUS);
        }
        if(counterOfPoints == 2){
            graphicsContext.strokeLine(pointsX.get(0).getVal(), pointsY.get(0).getVal(), pointsX.get(1).getVal(), pointsY.get(1).getVal());
            return;
        }
        if(counterOfPoints > 2) {
            System.out.println("отрисовка сплайна");
            Spline splineX = new Spline(pointsX, true);
            Spline splineY = new Spline(pointsY, false);
            List<Double> ptsX = splineX.getSpline();
            List<Double> ptsY = splineY.getSpline();
         //   System.out.println("Иксы" + Arrays.toString(ptsX.toArray()));
           // System.out.println("Игрики" +Arrays.toString(ptsY.toArray()));
            for (int i = 1; i < ptsX.size(); i++) {
           //     System.out.println("------");
                graphicsContext.strokeLine(ptsX.get(i - 1), ptsY.get(i - 1), ptsX.get(i), ptsY.get(i));
            }
        }
    }
}