package space.anomaly.Framework;

import space.anomaly.Controllers.*;
import space.anomaly.Math.PathPoint;
import space.anomaly.Math.Point;

import java.io.IOException;
import java.util.ArrayList;

public class Frame {

    public static void main(String[] args) throws InterruptedException, IOException {
        ArrayList<ArrayList<PathPoint>> sections = new ArrayList<ArrayList<PathPoint>>();
        ArrayList<PathPoint> path1 = new ArrayList<PathPoint>();
        path1.add(new PathPoint(0.0, 0.0, 1.0, 1.0, 1.5));
        path1.add(new PathPoint(3, 4, 1.0, 1.0, 1.5));
        path1.add(new PathPoint(6, 7, 0.5, 1.0, 1.5));
        path1.add(new PathPoint(8, 2, 1.0, 1.0, 1.5));

        ArrayList<PathPoint> path2 = new ArrayList<PathPoint>();
        path2.add(new PathPoint(4, -4, 1.0, 1.0, 1.5));
        path2.add(new PathPoint(1, 6, 1.0, 1.0, 1.5));
        path2.add(new PathPoint(-2, 3, 1.0, 1.0, 1.5));
        path2.add(new PathPoint(-4, 7, 1.0, 1.0, 1.5));
        path2.add(new PathPoint(-2, 9, 1.0, 1.0, 1.5));

        sections.add(path1);
        sections.add(path2);


        SectionedPurePursuit controller = new SectionedPurePursuit(sections, 1.0);

        controller.initGraph();
        Thread.sleep(4000);
        int i = 0;
        controller.run();

        controller.saveGraph();
//        System.out.println("loop num: " + i);
    }



}