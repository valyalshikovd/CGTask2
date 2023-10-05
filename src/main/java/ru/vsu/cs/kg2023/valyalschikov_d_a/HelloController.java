package ru.vsu.cs.kg2023.valyalschikov_d_a;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import ru.vsu.cs.kg2023.valyalschikov_d_a.spline.Line;

import java.util.List;

public class HelloController {
    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;
    double coefficient = 0.2;
    Line line = new Line(coefficient);

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
        line.addPointToLine(clickPoint.getX(), clickPoint.getY());
        drawSpline(graphicsContext);
    }
    private void drawSpline(GraphicsContext graphicsContext){
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        final int POINT_RADIUS = 3;
        for(int i = 0; i < line.getCounterOfPoints(); i++){
            graphicsContext.fillOval(
                    line.getPointsX().get(i).getVal() - POINT_RADIUS, line.getPointsY().get(i).getVal(),
                    2 * POINT_RADIUS, 2 * POINT_RADIUS);
        }
        if(line.getCounterOfPoints() == 2){
            graphicsContext.strokeLine(line.getPointsX().get(0).getVal(), line.getPointsY().get(0).getVal(), line.getPointsX().get(1).getVal(), line.getPointsY().get(1).getVal());
            return;
        }
        if(line.getCounterOfPoints() > 2) {
            List<Double> ptsX = line.getXPoints();
            List<Double> ptsY = line.getYPoints();
            for (int i = 1; i < ptsX.size(); i++) {
                graphicsContext.strokeLine(ptsX.get(i - 1), ptsY.get(i - 1), ptsX.get(i), ptsY.get(i));
            }
        }
    }
}