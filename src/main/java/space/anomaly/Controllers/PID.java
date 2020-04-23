package space.anomaly.Controllers;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import space.anomaly.Models.Unicycle;

import java.util.ArrayList;
import java.util.List;

public class PID extends Controller {
    public Unicycle model;
    public double Kp = 1.0;
    public double Ki = 1.0;
    public double Kd = 1.0;
    public double x = 0.0;
    public double y = 0.0;

    private List<Double> errorList = new ArrayList<Double>();
    private List<Integer> loopList = new ArrayList<Integer>();

    private double error, lastError, integral, derivative;

    public PID(){
        model = new Unicycle();
    }

    public PID(double x, double y) {
        model = new Unicycle();

        this.x = x;
        this.y = y;

    }

    @Override
    public void run() throws InterruptedException {

        error = Math.atan2(y - model.model_y, x - model.model_x) - model.model_theta;

        integral += ((error - lastError) / 2) * (model.loopTime / 1000.0);
        derivative = error - lastError;

        double output = Kp * error + Ki * integral + Kd * derivative;

        lastError = error;
        errorList.add(error);
        loopList.add(errorList.size());

        model.run(1.0, output);

        super.run();
    }

    @Override
    public void graph() {
        super.graph();

        List<XYChart> charts = new ArrayList<XYChart>();

        XYChart errorChart = new XYChartBuilder().title("error").build();

        errorChart.setTitle("Error plot");
        errorChart.setXAxisTitle("loop");
        errorChart.setYAxisTitle("error");
        errorChart.getStyler().setYAxisMin(-5.0);
        errorChart.getStyler().setYAxisMax(5.0);

        XYSeries series = chart.addSeries("x,y pos", model.xList, model.yList);

        XYSeries errorSeries = errorChart.addSeries("error", loopList, errorList);

        errorSeries.setLineColor(XChartSeriesColors.BLUE);
        errorSeries.setLineStyle(SeriesLines.SOLID);
        errorSeries.setMarker(SeriesMarkers.NONE);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setLineStyle(SeriesLines.SOLID);
        series.setMarker(SeriesMarkers.NONE);

        charts.add(chart);
        charts.add(errorChart);

        new SwingWrapper<XYChart>(charts).displayChartMatrix();
    }
}
