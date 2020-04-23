package space.anomaly.Controllers;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import space.anomaly.Math.Point;
import space.anomaly.Models.Differential;

import static space.anomaly.Math.MathFunctions.*;

import java.util.ArrayList;

public class BoundingBox extends Controller {
    public Differential model = new Differential();

    public ArrayList<Point> path = new ArrayList<Point>();

    public BoundingBox(){}

    public BoundingBox(ArrayList<Point> path){
        this.path = path;
    }



    @Override
    public void run() throws InterruptedException {
        double left = 0.0;
        double right = 0.0;

        double m1 = ((model.model_y +(Math.sin(model.model_theta) * -1.0)) - (model.model_y + Math.sin(model.model_theta))) /
                ((model.model_x + (Math.cos(model.model_theta) * -1.0)) - (model.model_x + Math.cos(model.model_theta)));

        double b1 = model.model_y - (m1 * model.model_x);


        ArrayList<Point> currentPath = chooseLine(path, m1, b1);

        double m2 = (currentPath.get(1).y - currentPath.get(0).y) / (currentPath.get(1).x - currentPath.get(0).x);
        double b2 = currentPath.get(0).y - (m2 * currentPath.get(0).x);

        double errorLeft = calcDistanceBetweenTwoLines(model.wheelBase, model.model_theta, new Point(model.model_x, model.model_y), m1, b1, m2, b2);
        double errorRight = calcDistanceBetweenTwoLines(model.wheelBase * -1.0, model.model_theta, new Point(model.model_x, model.model_y), m1, b1, m2, b2);

        left = -1.0 * errorLeft;
        right = -1.0 * errorRight;

        model.run(left, right);

        super.run();
    }

    @Override
    public void graph() {
        super.graph();

        XYSeries robotPath = chart.addSeries("robot path", model.xList, model.yList);
        robotPath.setLineColor(XChartSeriesColors.BLUE);
        robotPath.setLineStyle(SeriesLines.SOLID);
        robotPath.setMarker(SeriesMarkers.NONE);

        XYSeries pathPoints = chart.addSeries("path points", Point.toXList(path), Point.toYList(path));

        pathPoints.setLineColor(XChartSeriesColors.GREEN);
        pathPoints.setLineStyle(SeriesLines.DASH_DASH);
        pathPoints.setMarker(SeriesMarkers.DIAMOND);

        new SwingWrapper<XYChart>(chart).displayChart();
    }

    public ArrayList<Point> chooseLine(ArrayList<Point> path, double m1, double b1){
        ArrayList<Point> pathPoints = new ArrayList<Point>();

        double minError = Double.MAX_VALUE;

        for (int i = 0; i < path.size(); i ++) {
            Point startLine = path.get(i);
            Point endLine = path.get(i + 1);

            double m2 = (endLine.y - startLine.y) / (endLine.x - startLine.x);
            double b2 = startLine.y - (m2 * startLine.x);

            double error = calcDistanceBetweenTwoLines(model.wheelBase, model.model_theta, new Point(model.model_x, model.model_y), m1, b1, m2, b2) +
                            calcDistanceBetweenTwoLines(model.wheelBase * -1.0, model.model_theta, new Point(model.model_x, model.model_y), m1, b1, m2, b2);

            if(error < minError) {
                minError = error;
                pathPoints.clear();
                pathPoints.add(startLine);
                pathPoints.add(endLine);
            }
        }

        return pathPoints;

    }

}
