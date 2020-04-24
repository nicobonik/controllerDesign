package space.anomaly.Framework;

import space.anomaly.Controllers.BoundingBox;
import space.anomaly.Math.Point;

import java.util.ArrayList;

public class BoundingBoxFrame {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Point> points = new ArrayList<Point>();

        points.add(new Point(0, 0));
        points.add(new Point(4, 2));
        points.add(new Point(7, 4));

        BoundingBox controller = new BoundingBox(points);

        int i = 0;
        while(Math.abs((controller.model.model_x - 7)) + Math.abs((controller.model.model_y - 4)) > 0.1 && i < 9000){
            controller.run();

            i++;
        }

        controller.graph();

        System.out.println();
        System.out.println("loop num: " + i);
    }

}