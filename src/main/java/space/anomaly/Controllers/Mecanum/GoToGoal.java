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
import space.anomaly.Models.Mecanum;

import java.io.IOException;
import java.util.ArrayList;

public class GoToGoal extends Controller {
    Mecanum model;
    PathPoint point;
    XYSeries series;

    public GoToGoal(PathPoint point) {
        model = new Mecanum();
        this.point = point;
    }

    public GoToGoal(PathPoint point, double radius, double wheelBaseX, double wheelBaseY) {
        model = new Mecanum(radius, wheelBaseX, wheelBaseY, 1);
        this.point = point;
    }

    public void run() throws InterruptedException {
        while(Math.abs(model.model_x - point.x) > 0.1 && Math.abs(model.model_y - point.y) > 0.1) {
            goToGoal();
            updateGraph();
        }
    }

    public void goToGoal() throws InterruptedException {
        double absoluteAngleToPoint = Math.atan2(point.y - model.model_y, point.x - model.model_x);

        double robotAngleToPoint = MathFunctions.angleWrap(point.angle - model.model_theta);

        double turnSpeed = robotAngleToPoint * point.turnSpeed;

        model.run(Math.cos(absoluteAngleToPoint) * point.speed, Math.sin(absoluteAngleToPoint) * point.speed, turnSpeed);

    }

    public void graph() {
        super.graph();
        window = new SwingWrapper<XYChart>(chart);

        window.displayChart();

        series = chart.addSeries("robot position", model.xList, model.yList);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setLineStyle(SeriesLines.SOLID);
        series.setMarker(SeriesMarkers.NONE);
    }

    public void updateGraph() {

        chart.updateXYSeries("robot position", model.xList, model.yList, null);
        window.repaintChart();

    }

    public static void main(String[] args) throws InterruptedException, IOException {
        GoToGoal controller = new GoToGoal(new PathPoint(1, 2, 0.5, 1, 0, Math.PI / 2.0), 1, 1, 1);

        controller.graph();

        controller.run();

        controller.saveGraph("mecanum_go_to_goal_1");
    }

}
