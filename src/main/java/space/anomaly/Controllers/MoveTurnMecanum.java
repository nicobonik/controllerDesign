package space.anomaly.Controllers;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import space.anomaly.Math.MathFunctions;
import space.anomaly.Math.PID;
import space.anomaly.Math.PathPoint;
import space.anomaly.Math.Point;
import space.anomaly.Models.Mecanum;

import java.nio.file.Path;
import java.util.ArrayList;

public class MoveTurnMecanum extends Controller {
    public Mecanum model;

    private PID turnPID = new PID(1.0, 1.0, 1.0);
    private PID movePID = new PID(1.0, 1.0, 1.0);

    public ArrayList<PathPoint> path;
    XYSeries points;
    XYSeries series;

    public MoveTurnMecanum(ArrayList<PathPoint> path) {
        model = new Mecanum();
        this.path = path;
    }

    public MoveTurnMecanum(ArrayList<PathPoint> path, double radius, double wheelBaseX, double wheelBaseY) {
        model = new Mecanum(radius, wheelBaseX, wheelBaseY, 1);
        this.path = path;
    }

    public void run() throws InterruptedException {
        PathPoint lastPoint = new PathPoint();
        for (PathPoint point : path) {
            turnToPoint(point);
            moveToPoint(point, lastPoint);
            lastPoint.setPathPoint(point);
            turnPID.reset();
            movePID.reset();
        }
    }

    public void turnToPoint(PathPoint point) throws InterruptedException {
        double relativeAngleToPoint = Double.MAX_VALUE;
        turnPID.Kp = point.turnSpeed * Math.signum(turnPID.Kp);
        while(Math.abs(relativeAngleToPoint) > 0.1) {
            double absoluteAngleToPoint = Math.atan2(point.y - model.model_y, point.x - model.model_x);
            relativeAngleToPoint = MathFunctions.angleWrap(absoluteAngleToPoint - model.model_theta);
            double output = turnPID.run(relativeAngleToPoint, model.loopTime);
            model.run(0, 0, output);
            updateGraph();
        }
    }

    public void moveToPoint(PathPoint point, PathPoint lastPoint) throws InterruptedException {
        movePID.Kp = point.speed * Math.signum(movePID.Kp);
        while(Math.abs(model.model_x - point.x) > 0.1 && Math.abs(model.model_y - point.y) > 0.1) {
            PathPoint follow = findFollowPoint(lastPoint, point);

            double absoluteAngleToPoint = Math.atan2(follow.y - model.model_y, follow.x - model.model_x);
            double relativeAngleToPoint = MathFunctions.angleWrap(absoluteAngleToPoint - model.model_theta);

            double output = movePID.run(relativeAngleToPoint, model.loopTime);
            model.run(point.speed, point.speed, output);
            updateGraph();
        }
    }

    public PathPoint findFollowPoint(PathPoint start, PathPoint end) {
        PathPoint followPoint = new PathPoint(end);

        ArrayList<Point> circleIntersections = MathFunctions.lineCircleIntersectNoBoundingBox(start.toPoint(), end.toPoint(), end.lookAhead, new Point(model.model_x, model.model_y));

        double closestAngle = Double.MAX_VALUE;

        for (Point intersection : circleIntersections) {
            double angle = Math.atan2(intersection.y - model.model_y, intersection.x - model.model_x);
            double deltaAngle = Math.abs(MathFunctions.angleWrap(angle - model.model_theta));

            if(deltaAngle < closestAngle) {
                closestAngle = deltaAngle;
                followPoint.setPoint(intersection);
            }
        }

        return followPoint;
    }

    public void graph() {
        super.graph();
        window = new SwingWrapper<XYChart>(chart);

        window.displayChart();

        points = chart.addSeries("path points", PathPoint.toXList(path), PathPoint.toYList(path));

        points.setLineColor(XChartSeriesColors.GREEN);
        points.setLineStyle(SeriesLines.DASH_DASH);
        points.setMarker(SeriesMarkers.DIAMOND);

        series = chart.addSeries("robot position", model.xList, model.yList);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setLineStyle(SeriesLines.SOLID);
        series.setMarker(SeriesMarkers.NONE);
    }

    public void updateGraph() {
        chart.updateXYSeries("robot position", model.xList, model.yList, null);
        window.repaintChart();
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<PathPoint> path = new ArrayList<>();
        path.add(new PathPoint(1, 1, 1, 1, 1));
        path.add(new PathPoint(2, 4, 1, 1, 1));
        path.add(new PathPoint(6, 2, 1, 1, 1));
        path.add(new PathPoint(3, 3, 1, 1, 1));

        MoveTurnMecanum controller = new MoveTurnMecanum(path, 1.0, 1.0, 1.0);

        controller.graph();

        controller.run();


    }


}
