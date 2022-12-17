package com.kodilla.xo.JFX;

import com.kodilla.xo.board.PositionConverter;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class ShapeAdder {
    public static void addCircle(Group root, int position, int size, Rectangle board) {
        Circle circle = new Circle();
        circle.setStroke(Paint.valueOf("White"));
        circle.setStrokeWidth(3);
        circle.setFill(Paint.valueOf("Blue"));

        double sizeX = board.getWidth() / size;
        double sizeY = board.getHeight() / size;
        int row = PositionConverter.positionToRow(position, size);
        int column = PositionConverter.positionToColumn(position, size);

        circle.setCenterX(board.getX() + sizeX/2 + column*sizeX);
        circle.setCenterY(board.getY() + sizeY/2 + row*sizeY);
        circle.setRadius(sizeX/3);

        root.getChildren().add(circle);
    }

    public static void addCross(Group root, int position, int size, Rectangle board) {
        Line line = new Line();
        Line linePerpendicular = new Line();

        line.setStroke(Paint.valueOf("White"));
        line.setStrokeWidth(3);
        linePerpendicular.setStroke(Paint.valueOf("White"));
        linePerpendicular.setStrokeWidth(3);

        double sizeX = board.getWidth() / size;
        double sizeY = board.getHeight() / size;
        int row = PositionConverter.positionToRow(position, size);
        int column = PositionConverter.positionToColumn(position, size);

        double lineLength = sizeX/1.5;
        double crossStartOffsetX = (sizeX - lineLength)/2;
        double crossStartOffsetY = (sizeY - lineLength)/2;

        line.setStartX(board.getX() + crossStartOffsetX + column*sizeX);
        line.setEndX(board.getX() + crossStartOffsetX + lineLength + column*sizeX);
        line.setStartY(board.getY() + crossStartOffsetY + row*sizeY);
        line.setEndY(board.getY() + crossStartOffsetY + lineLength + row*sizeX);

        linePerpendicular.setStartX(line.getEndX());
        linePerpendicular.setEndX(line.getStartX());
        linePerpendicular.setStartY(line.getStartY());
        linePerpendicular.setEndY(line.getEndY());

        root.getChildren().add(line);
        root.getChildren().add(linePerpendicular);
    }
}
