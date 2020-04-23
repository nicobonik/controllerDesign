package space.anomaly.Models;

import static java.lang.Math.*;

public class Differential extends Model {
    public double radius = 1.0;
    public double wheelBase = 1.0;

    public Differential(){
        model_x = 0;
        model_y = 0;
        model_theta = 0;
        loopTime = 5;
    }

    public Differential(double radius, double wheelBase) {
        this();
        this.radius = radius;
        this.wheelBase = wheelBase;
    }



    public void run(double left, double right) throws InterruptedException {

        double v = (radius / 2) * (left + right);

        double w = (radius / wheelBase) * (left - right);

        v = clip(v, -1.0, 1.0);
        w = clip(w, -1.0, 1.0);

        v *= (double) loopTime / 1000.0;
        w *= (double) loopTime / 1000.0;

        model_x += v * cos(model_theta);
        model_y += v * sin(model_theta);

        model_theta += w;

        super.run();
    }

    private double clip(double x, double min, double max) {
        if(x < min) {
            return min;
        } else if(x > max) {
            return max;
        } else {
            return x;
        }
    }
}
