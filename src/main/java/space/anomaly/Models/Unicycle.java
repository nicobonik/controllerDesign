package space.anomaly.Models;

import space.anomaly.Framework.Message;
import space.anomaly.Framework.MessageHandler;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class Unicycle extends Model {

    public Unicycle(){
        model_x = 0;
        model_y = 0;
        model_theta = -PI / 2.0;
        loopTime = 5;
    }

    public void run(double v, double w) throws InterruptedException {
        v = clip(v, -1.0, 1.0);
        w = clip(w, -1.0, 1.0);

        v *= (double) loopTime / 1000.0;
        w *= (double) loopTime / 1000.0;
        model_x += v * cos(model_theta);
        model_y += v * sin(model_theta);
        model_theta += w;

        super.run();
    }

    public static double clip(double x, double min, double max) {

        if(x < min) {
            return min;
        } else if(x > max) {
            return max;
        } else {
            return x;
        }

    }


}
