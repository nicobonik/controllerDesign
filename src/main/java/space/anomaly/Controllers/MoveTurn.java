package space.anomaly.Controllers;

import org.knowm.xchart.BitmapEncoder;
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
import space.anomaly.Models.Differential;

import java.io.IOException;
import java.util.ArrayList;

public class MoveTurn extends Controller {
    public Differential model;

    private PID turnPID = new PID(1.0, 1.0, 1.0);
    private PID movePID = new PID(1.0, 1.0, 1.0);

    public ArrayList<PathPoint> path = new ArrayList<>();
    XYSeries points;
    XYSeries series;

    public MoveTurn(ArrayList<PathPoint> path) {
        model = new Differential();
        this.path = path;
    }

    public MoveTurn(ArrayList<PathPoint> path, double radius, double wheelBase) {
        model = new Differential(radius, wheelBase);
        this.path = path;
    }

    public void run() throws InterruptedException {
        for (PathPoint point : path) {
            System.out.println("\nturning!");
            turnToPoint(point);
            System.out.println("\nmoving!");
            moveToPoint(point);
        }


    }

    public void turnToPoint(PathPoint point) throws InterruptedException {

       double relativeAngleToPoint = Double.MAX_VALUE;
        turnPID.Kp = point.turnSpeed * Math.signum(turnPID.Kp);
        while(Math.abs(relativeAngleToPoint) > 0.1) {
            double absoluteAngleToPoint = Math.atan2(point.y - model.model_y, point.x - model.model_x);
            relativeAngleToPoint = MathFunctions.angleWrap(absoluteAngleToPoint - model.model_theta);
            double output = turnPID.run(relativeAngleToPoint, model.loopTime);
            model.run(output, -output);
            updateGraph();
        }
    }

    public void moveToPoint(PathPoint point) throws InterruptedException {

        double relativeAngleToPoint;
        movePID.Kp = point.speed * Math.signum(movePID.Kp);

        while(Math.abs(model.model_x - point.x) > 0.1 && Math.abs(model.model_y - point.y) > 0.1) {
            double absoluteAngleToPoint = Math.atan2(point.y - model.model_y, point.x - model.model_x);
            relativeAngleToPoint = MathFunctions.angleWrap(absoluteAngleToPoint - model.model_theta);

            double output = movePID.run(relativeAngleToPoint, model.loopTime);
            model.run(point.speed + output, point.speed - output);
            updateGraph();
        }

    }

    //these are kinda useless rn
    public double getRobotRelativeAngleToPoint(Point point) {
        double absoluteAngleToPoint = Math.atan2(point.y - model.model_y, point.x - model.model_x);
        return MathFunctions.angleWrap(model.model_theta - absoluteAngleToPoint);
    }

    public double getPointAbsoluteAngleToPoint(Point p1, Point p2) {
        return MathFunctions.angleWrap(Math.atan2(p2.y - p1.y, p2.x - p1.x));
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

   public void saveGraph() throws IOException {
        saveGraph("move_turn_model");
   }

   public void saveGraph(String filename) throws IOException {
       BitmapEncoder.saveBitmapWithDPI(chart, "./" + filename, BitmapEncoder.BitmapFormat.PNG, 300);
       System.out.println("\nsaved graph");
   }

    public static void main(String[] args) throws InterruptedException, IOException {
        ArrayList<PathPoint> path = new ArrayList<>();
//        path.add(new PathPoint(0, 0, 1, 1));
        path.add(new PathPoint(2, 2, 1, 1));
        path.add(new PathPoint(4, 8, 1, 1));
        path.add(new PathPoint(8, 0, 1, 1));
        path.add(new PathPoint(0, 8, 1, 1));

        MoveTurn controller = new MoveTurn(path, 5.0, 1.0);

        controller.graph();

        controller.run();

        controller.saveGraph();

    }

}
