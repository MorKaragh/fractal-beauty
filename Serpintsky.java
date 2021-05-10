package samples;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;


public class Serpintsky extends Application {

    private static List<Point> startingPoints = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();

        Canvas canvas = new Canvas();
        canvas.setHeight(1000);
        canvas.setWidth(1000);
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        graphicsContext2D.setFill(Color.web("#d4bba9"));
        graphicsContext2D.fillRect(0, 0, 1000, 1000);
        graphicsContext2D.setFill(Color.PURPLE);
        canvas.setOnMouseClicked(mouseEvent -> {
            if (startingPoints.size() < 3) {
                Point point = new Point(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                startingPoints.add(point);
                graphicsContext2D.fillOval(point.x, point.y, 5, 5);
                graphicsContext2D.fillText(String.valueOf("ABC".charAt(startingPoints.size()-1)), point.x-10, point.y-10);
            } else {
                Point point = new Point(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                startingPoints.add(point);
                graphicsContext2D.fillOval(point.x, point.y, 5, 5);
                draw1000points(graphicsContext2D);
            }
        });

        root.getChildren().add(canvas);

        Scene scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);
        stage.show();

    }

    private void draw1000points(GraphicsContext graphicsContext) {
        Timeline timeline = new Timeline();
        int interval = 1; // milliseconds
        AtomicReference<Point> activePoint = new AtomicReference<>(startingPoints.get(3));
        graphicsContext.setFill(Color.GREEN);
        timeline.setCycleCount(200000);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(interval),
                e -> {
                    Point randomPoint = startingPoints.get(ThreadLocalRandom.current().nextInt(0, 3));

                    activePoint.set(getPointBetween(activePoint.get(), randomPoint));

                    double x = activePoint.get().x;
                    double y = activePoint.get().y;
                    graphicsContext.fillOval(x, y, 1, 1);
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private Point getPointBetween(Point activePoint, Point randomPoint) {
        double x = Math.min(activePoint.x, randomPoint.x) + (Math.max(activePoint.x, randomPoint.x) - Math.min(activePoint.x, randomPoint.x)) / 2;
        double y = Math.min(activePoint.y, randomPoint.y) + (Math.max(activePoint.y, randomPoint.y) - Math.min(activePoint.y, randomPoint.y)) / 2;
        return new Point(x, y);
    }

    private class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
