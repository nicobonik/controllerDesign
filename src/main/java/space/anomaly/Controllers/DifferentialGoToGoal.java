package space.anomaly.Controllers;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import space.anomaly.Models.Differential;

public class DifferentialGoToGoal extends Controller {
    public Differential model;

    public double x = 0.0;
    public double y = 0.0;

    public DifferentialGoToGoal() {
        model = new Differential();

    }

    public DifferentialGoToGoal(double x, double y){
        model = new Differential();
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() throws InterruptedException {
        double vl, vr;
        double K = 1.0;
        double error = Math.atan2(y - model.model_y, x - model.model_x) - model.model_theta;

        vl = K * (1 + error);
        vr = K * (1 - error);


        model.run(vl, vr);

        super.run();
    }

    @Override
    public void graph() {
        super.graph();

        XYSeries series = chart.addSeries("robot position", model.xList, model.yList);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setLineStyle(SeriesLines.SOLID);
        series.setMarker(SeriesMarkers.NONE);

        XYSeries points = chart.addSeries("path points", new double[] {0.0, x}, new double[] {0.0, y});

        points.setLineColor(XChartSeriesColors.GREEN);
        points.setLineStyle(SeriesLines.DASH_DASH);
        points.setMarker(SeriesMarkers.DIAMOND);

        new SwingWrapper<XYChart>(chart).displayChart();

    }
}
