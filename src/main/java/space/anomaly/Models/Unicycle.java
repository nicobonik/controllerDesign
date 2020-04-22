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
        model_theta = Math.PI / 2.0;
        loopTime = 5;
    }

    public void run(double v, double w) throws InterruptedException {
        v *= (double) loopTime / 1000.0;
        w *= (double) loopTime / 1000.0;
        model_x += v * cos(model_theta);
        model_y += v * sin(model_theta);
        model_theta += w;

        super.run(v, w);
    }




}
