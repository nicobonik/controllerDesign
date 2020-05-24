package space.anomaly.Models;

import static java.lang.Math.*;

/**
 * An example model for a fairly common differential drive robot
 */
public class Differential extends Model {

    /**
     * the radius of the model's wheels.
     */
    public double radius = 1.0;
    /**
     * the distance between the left and right wheels.
     */
    public double wheelBase = 1.0;


    /**
     * default constructor.
     */
    public Differential() {
        model_x = 0;
        model_y = 0;
        model_theta = 0;
        loopTime = 5;
    }

    /**
     * Constructor with the physical parameters of the simulated robot model.
     * @param radius  the radius of the wheels.
     * @param wheelBase  the distance between the left and right wheels.
     */
    public Differential(double radius, double wheelBase) {
        this();
        this.radius = radius;
        this.wheelBase = wheelBase;
    }


    /**
     * The run method specific to this model, runs it's code first before running {@link Model}{@code .run();}
     *
     * @param left  the power of the left wheel, from -1.0 to 1.0
     * @param right the power of the right wheel, from -1.0 to 1.0
     * @throws InterruptedException if the thread is interrupted during a sleep defined in ms by {@code loopTime}.
     */
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
        if (x < min) {
            return min;
        } else if (x > max) {
            return max;
        } else {
            return x;
        }
    }
}
