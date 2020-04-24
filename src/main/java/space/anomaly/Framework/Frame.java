package space.anomaly.Framework;

import space.anomaly.Controllers.*;
import space.anomaly.Math.Point;

import java.util.ArrayList;

public class Frame {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Point> path = new ArrayList<Point>();
        path.add(new Point(0, 0));
        path.add(new Point(1, 4));
        path.add(new Point(2, 7));
        path.add(new Point(3, 0));
        path.add(new Point(4, -5));
//        path.add(new Point(-3, 3));

        PurePursuit controller = new PurePursuit(path, 1.0);

        controller.initGraph();
        Thread.sleep(4000);
        int i = 0;
        while(i < 12000) {
            controller.run();
            i++;
        }

//        System.out.println("loop num: " + i);
    }

}