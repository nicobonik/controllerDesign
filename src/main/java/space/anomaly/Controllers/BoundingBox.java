package space.anomaly.Controllers;

import space.anomaly.Math.Point;
import space.anomaly.Models.Model;
import space.anomaly.Models.Unicycle;

import java.util.ArrayList;

public class BoundingBox extends Controller {
    public Unicycle model = new Unicycle();

    public ArrayList<Point> path = new ArrayList<Point>();

    public BoundingBox(){}

    @Override
    public void run() throws InterruptedException {



        super.run();
    }
}
