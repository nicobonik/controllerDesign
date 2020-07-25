package space.anomaly.Controllers.Mecanum;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import space.anomaly.Controllers.Controller;
import space.anomaly.Math.MathFunctions;
import space.anomaly.Math.PathPoint;
import space.anomaly.Math.Point;
import space.anomaly.Models.Mecanum;

import java.util.ArrayList;

public class PurePursuit extends Controller {

    public Mecanum model;

    public ArrayList<ArrayList<PathPoint>> sections;
    XYSeries points;
    XYSeries series;

    public PurePursuit(ArrayList<ArrayList<PathPoint>> sections) {
        this.sections = sections;
    }

    public PurePursuit(ArrayList<ArrayList<PathPoint>> sections, double radius) {
        this.sections = sections;
        model = new Mecanum(radius, 1, 1, 1);
    }

    public void run() throws InterruptedException {
        for (ArrayList<PathPoint> section : sections) {

            while(Math.abs(model.model_x - section.get(section.size() - 1).x) + Math.abs(model.model_y - section.get(section.size() - 1).y) > 0.1) {
                PathPoint point = findFollowPoint(section);
                moveToPoint(point);
                updateGraph();
            }
        }
    }

    public PathPoint findFollowPoint(ArrayList<PathPoint> path) {
        PathPoint followPoint = new PathPoint(path.get(0));

        ArrayList<Point> circleIntersections;

        for(int i = 0; i < path.size() - 1; i++) {
            PathPoint start = path.get(i);
            PathPoint end = path.get(i + 1);


            if(path.indexOf(end) == path.size() - 1) {

                circleIntersections = MathFunctions.lineCircleIntersectNoBoundingBox(start.toPoint(), end.toPoint(), end.lookAhead, new Point(model.model_x, model.model_y));

                double closestAngle = Double.MAX_VALUE;
                for(Point intersection : circleIntersections) {
                    double angle = Math.atan2(intersection.y - model.model_y, intersection.x - model.model_x);
                    double relativePointAngle = Math.atan2(end.y - start.y, end.x - start.x);
                    double deltaAngle = Math.abs(MathFunctions.angleWrap(angle - relativePointAngle));

                    if(deltaAngle < closestAngle) {
                        closestAngle = deltaAngle;
                        followPoint.setPathPoint(end);
                        followPoint.setPoint(intersection);
                    }
                }

            } else {

                circleIntersections = MathFunctions.lineCircleIntersect(start.toPoint(), end.toPoint(), end.lookAhead, new Point(model.model_x, model.model_y));

                double closestAngle = Double.MAX_VALUE;
                for(Point intersection : circleIntersections) {
                    double angle = Math.atan2(intersection.y - model.model_y, intersection.x - model.model_x);
                    double relativePointAngle = Math.atan2(end.y - start.y, end.x - start.x);
                    double deltaAngle = Math.abs(MathFunctions.angleWrap(angle - relativePointAngle));

                    if(deltaAngle < closestAngle) {
                        closestAngle = deltaAngle;
                        followPoint.setPathPoint(end);
                        followPoint.setPoint(intersection);
                    }
                }
            }

        }
        System.out.println("\n" + followPoint.x +  ", " + followPoint.y);
//
        return followPoint;
    }

    public void moveToPoint(PathPoint point) throws InterruptedException {
       double absoluteAngleToPoint = Math.atan2(point.y - model.model_y, point.x - model.model_x);

        double robotAngleToPoint = point.angle - model.model_theta;

        double turnSpeed = robotAngleToPoint * point.turnSpeed;

        model.run(Math.cos(absoluteAngleToPoint) * point.speed, Math.sin(absoluteAngleToPoint) * point.speed, turnSpeed);
    }

    public void graph() {
        super.graph();

        window = new SwingWrapper<XYChart>(chart);

        window.displayChart();

        points = chart.addSeries("path points", generateXPath(sections), generateYPath(sections));

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

    public ArrayList<Double> generateXPath(ArrayList<ArrayList<PathPoint>> sections) {
        ArrayList<Double> points = new ArrayList<Double>();

        for (ArrayList<PathPoint> path : sections) {

            for (PathPoint point : path) {
                points.add(point.x);
            }

        }

        return points;
    }

    public ArrayList<Double> generateYPath(ArrayList<ArrayList<PathPoint>> sections) {
        ArrayList<Double> points = new ArrayList<Double>();

        for (ArrayList<PathPoint> path : sections) {

            for (PathPoint point : path) {
                points.add(point.y);
            }

        }

        return points;
    }

    public static void main(String[] args) throws InterruptedException {

        ArrayList<ArrayList<PathPoint>> sections = new ArrayList<>();
        ArrayList<PathPoint> section1 = new ArrayList<>();
        section1.add(new PathPoint(1, 1, 1, 1, 1, 0));
        section1.add(new PathPoint(3, 2, 1, 1, 1, 0));
        section1.add(new PathPoint(4, 6, 1, 1, 1, Math.PI));
        section1.add(new PathPoint(7, 3, 1, 1, 1,  Math.PI / 2.0));
        section1.add(new PathPoint(9, -4, 1, 1, 1, 0));

        ArrayList<PathPoint> section2 = new ArrayList<>();
        section2.add(new PathPoint(9, -4, 1, 1, 1, 0));
        section2.add(new PathPoint(7, 3, 1, 1, 1, 0));
        section2.add(new PathPoint(4, 6, 1, 1, 1, 0));
        section2.add(new PathPoint(3, 2, 1, 1, 1, 0));
        section2.add(new PathPoint(1, 1, 1, 1, 1, 0));

        sections.add(section1);
        sections.add(section2);

        PurePursuit controller = new PurePursuit(sections, 1);

        controller.graph();
        controller.run();
    }
}
