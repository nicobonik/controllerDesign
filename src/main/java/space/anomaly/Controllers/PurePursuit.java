package space.anomaly.Controllers;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import space.anomaly.Math.MathFunctions;
import space.anomaly.Math.PathPoint;
import space.anomaly.Math.Point;
import space.anomaly.Models.Differential;

import java.util.ArrayList;

public class PurePursuit extends Controller {
    public Differential model;

    public ArrayList<PathPoint> path;
    public double radius = 1.0;

    XYSeries points;
    XYSeries series;

    XYChart errorChart;

    public PurePursuit() {
        model = new Differential();
        path = new ArrayList<PathPoint>();
    }

    public PurePursuit(ArrayList<PathPoint> path, double radius) {
        this();
        this.path = path;
        this.radius = radius;
    }

    @Override
    public void run() throws InterruptedException {

        while(Math.abs(model.model_x - path.get(path.size() - 1).x) + Math.abs(model.model_y - path.get(path.size() - 1).y) > radius + 0.5) {
            PathPoint followPoint = findFollowPoint(path);

            goToGoal(followPoint.toPoint(), followPoint.speed);

            super.run();
            updateGraph();
        }
    }

    public void initGraph() {
        super.graph();

        errorChart = new XYChartBuilder().title("error chart").build();

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

    public PathPoint findFollowPoint(ArrayList<PathPoint> path){
        PathPoint followPoint = new PathPoint(path.get(0));

        ArrayList<Point> circleIntersections;

        for (int i = 0; i < path.size() - 1; i++) {
            PathPoint startLine = path.get(i);
            PathPoint endLine = path.get(i + 1);

            circleIntersections = MathFunctions.lineCircleIntersect(startLine.toPoint(), endLine.toPoint(), startLine.lookAhead, new Point(model.model_x, model.model_y));

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
        double angleToTarget = MathFunctions.angleWrap(Math.atan2(goal.y - model.model_y, goal.x - model.model_x) - model.model_theta);

        double error = (angleToTarget / Math.PI) * speed;

        vl = K * (speed + error);
        vr = K * (speed - error);


        model.run(vl, vr);

    }
}
