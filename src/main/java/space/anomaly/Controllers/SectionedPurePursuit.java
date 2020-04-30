package space.anomaly.Controllers;

import org.knowm.xchart.*;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import space.anomaly.Math.MathFunctions;
import space.anomaly.Math.PathPoint;
import space.anomaly.Math.Point;
import space.anomaly.Models.Differential;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SectionedPurePursuit extends Controller {
    public Differential model;

    public PID pid = new PID(10.0, 0, 0);

    public ArrayList<ArrayList<PathPoint>> sections;
    public double radius = 1.0;

    XYSeries points;
    XYSeries series;
    XYSeries errorSeries;

    ArrayList<Double> errorList = new ArrayList<Double>();
    ArrayList<Integer> loopNum = new ArrayList<Integer>();

    XYChart errorChart;

    public SectionedPurePursuit() {
        model = new Differential();
        sections = new ArrayList<ArrayList<PathPoint>>();
        errorList.add(0.0);
        loopNum.add(0);

    }

    public SectionedPurePursuit(ArrayList<ArrayList<PathPoint>> sections, double radius) {
        this();
        this.sections = sections;
        this.radius = radius;
    }

    @Override
    public void run() throws InterruptedException {
        for (ArrayList<PathPoint> path : sections) {

            while(Math.abs(model.model_x - path.get(path.size() - 1).x) + Math.abs(model.model_y - path.get(path.size() - 1).y) > radius) {
                PathPoint followPoint = findFollowPoint(path);

                goToGoal(followPoint.toPoint(), followPoint.speed);

                updateGraph();
            }
        }

    }

    public void initGraph() {
        super.graph();

        errorChart = new XYChartBuilder().title("error chart").build();

        errorChart.setTitle("Error plot");
        errorChart.setXAxisTitle("loop");
        errorChart.setYAxisTitle("error");


        List<XYChart> charts = new ArrayList<XYChart>();
        charts.add(chart);
        charts.add(errorChart);

        window = new SwingWrapper<XYChart>(charts);
        window.displayChartMatrix();

        points = chart.addSeries("path points", generateXPath(sections), generateYPath(sections));

        points.setLineColor(XChartSeriesColors.GREEN);
        points.setLineStyle(SeriesLines.DASH_DASH);
        points.setMarker(SeriesMarkers.DIAMOND);

        series = chart.addSeries("robot position", model.xList, model.yList);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setLineStyle(SeriesLines.SOLID);
        series.setMarker(SeriesMarkers.NONE);

        errorSeries = errorChart.addSeries("error list", loopNum, errorList);

        errorSeries.setLineColor(XChartSeriesColors.BLUE);
        errorSeries.setLineStyle(SeriesLines.SOLID);
        errorSeries.setMarker(SeriesMarkers.NONE);

    }

    public void updateGraph() {
        chart.updateXYSeries("robot position", model.xList, model.yList, null);
        errorChart.updateXYSeries("error list", loopNum, errorList, null);

        window.repaintChart(0);
        window.repaintChart(1);
    }

    public void saveGraph() throws IOException {
        BitmapEncoder.saveBitmapWithDPI(chart, "./pure_pursuit_1", BitmapEncoder.BitmapFormat.PNG, 300);
        BitmapEncoder.saveBitmapWithDPI(errorChart, "./pure_pursuit_error1", BitmapEncoder.BitmapFormat.PNG, 300);
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

        errorList.add(error);
        loopNum.add(errorList.size());

        vl = K * (speed + error);
        vr = K * (speed - error);


        model.run(vl, vr);

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

}
