package space.anomaly.Controllers;

import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import space.anomaly.Math.MathFunctions;
import space.anomaly.Math.Point;
import space.anomaly.Models.Differential;

import java.util.ArrayList;

public class PurePursuit extends Controller {
    public Differential model;

    public ArrayList<Point> path;
    public double radius = 1.0;

    XYSeries points;
    XYSeries series;

    public PurePursuit() {
        model = new Differential();
        path = new ArrayList<Point>();
    }

    public PurePursuit(ArrayList<Point> path, double radius) {
        this();
        this.path = path;
        this.radius = radius;
    }

    @Override
    public void run() throws InterruptedException {

        Point followPoint = findFollowPoint(path, radius);

        goToGoal(followPoint, 1.0);

        super.run();
        updateGraph();
    }

    public void initGraph() {
        super.graph();

        points = chart.addSeries("path points", Point.toXList(path), Point.toYList(path));

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

    public Point findFollowPoint(ArrayList<Point> path, double radius){
        Point followPoint = new Point(path.get(0));

        ArrayList<Point> circleIntersections;

        for (int i = 0; i < path.size() - 1; i++) {
            Point startLine = path.get(i);
            Point endLine = path.get(i + 1);

            circleIntersections = MathFunctions.lineCircleIntersect(startLine, endLine, radius, new Point(model.model_x, model.model_y));

            double closestAngle = Double.MAX_VALUE;

            for (Point intersection : circleIntersections) {
                double angle = Math.atan2(intersection.y - model.model_y, intersection.x - model.model_x);
                double deltaAngle = Math.abs(MathFunctions.angleWrap(angle - model.model_theta));

                if(deltaAngle < closestAngle) {
                    closestAngle = deltaAngle;
                    followPoint.setPoint(intersection);
                }
            }
        }

        return followPoint;
    }


    public void goToGoal(Point goal, double speed) throws InterruptedException {
        double vl, vr;
        double K = 2.0;
        double error = Math.atan2(goal.y - model.model_y, goal.x - model.model_x) - model.model_theta;

        vl = K * (speed + error);
        vr = K * (speed - error);


        model.run(vl, vr);

    }
}
