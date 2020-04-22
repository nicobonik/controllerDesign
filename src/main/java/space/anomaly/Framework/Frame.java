package space.anomaly.Framework;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import space.anomaly.Models.Unicycle;

import java.awt.*;

public class Frame {

    public static void main(String[] args) throws InterruptedException {
        Unicycle unicycle = new Unicycle();
        long startTime = System.currentTimeMillis();
        long i = 0;
        while(i < 500) {
            unicycle.run(2, 0.5);
            i++;
        }

        XYChart chart = new XYChartBuilder().title("robot pos").xAxisTitle("X").yAxisTitle("Y").build();

        chart.getStyler().setYAxisMin(-10.0);
        chart.getStyler().setYAxisMax(10.0);
        chart.getStyler().setXAxisMin(-10.0);
        chart.getStyler().setXAxisMax(10.0);

        XYSeries series = chart.addSeries("xy data", unicycle.xList, unicycle.yList);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setLineStyle(SeriesLines.SOLID);
        series.setMarker(SeriesMarkers.NONE);

        new SwingWrapper<XYChart>(chart).displayChart();

        System.out.println();
        System.out.println("loop num: " + i);
    }

}