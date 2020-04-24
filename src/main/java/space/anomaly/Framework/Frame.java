package space.anomaly.Framework;

import space.anomaly.Controllers.*;
import space.anomaly.Math.PathPoint;
import space.anomaly.Math.Point;

import java.util.ArrayList;

public class Frame {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<PathPoint> path = new ArrayList<PathPoint>();
        path.add(new PathPoint(0.0, 0.0, 1.0, 1.0, 1.0));
        path.add(new PathPoint(3, 4, 1.0, 1.0, 1.0));
        path.add(new PathPoint(6, 7, 1.0, 1.0, 1.5));
        path.add(new PathPoint(8, 2, 1.0, 1.0, 1.5));
        path.add(new PathPoint(4, -4, 1.0, 1.0, 1.0));

        PurePursuit controller = new PurePursuit(path, 1.0);

        controller.initGraph();
        Thread.sleep(4000);
        int i = 0;
        controller.run();
//        System.out.println("loop num: " + i);
    }

}