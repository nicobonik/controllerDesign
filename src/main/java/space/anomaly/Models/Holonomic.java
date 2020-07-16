package space.anomaly.Models;

public class Holonomic extends Model {

    public Holonomic() {}

    public void run(double x, double y, double w) throws InterruptedException {

        x = clip(x, -1.0, 1.0);
        y = clip(y, -1.0, 1.0);
        w = clip(w, -1.0, 1.0);

        x = (double) loopTime / 1000.0;
        y = (double) loopTime / 1000.0;
        w = (double) loopTime / 1000.0;

        model_x += x;
        model_y += y;
        model_theta += w;

        super.run();
    }

}
