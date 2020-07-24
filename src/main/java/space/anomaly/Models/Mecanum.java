package space.anomaly.Models;

/**
 * Mecanum Holonomic Model -- assumes the use of 45 degree rollers.
 *
 * A holonomic kinematic model uses tangent ratios of the roller angles on the wheels,
 * so 45 degree holonomics make all the tangent ratios equal to 1 and the math is much easier.
 */
public class Mecanum extends Model {

    /**
     * radius of the wheel
     */
    private double radius = 1.0;

    /**
     * Wheel base for mecanum is HALF of the wheel base, measure to the center and not the other wheel.
     */
    private double wheelBaseX = 1.0;
    private double wheelbaseY = 1.0;

    private double multiplier;

    public Mecanum() {
        model_x = 0;
        model_y = 0;
        model_theta = 0;
        loopTime = 5;
    }

    public Mecanum(double radius, double wheelBaseX, double wheelbaseY, double multiplier) {
        this();

        this.radius = radius;
        this.wheelBaseX = wheelBaseX;
        this.wheelbaseY = wheelbaseY;
        this.multiplier = multiplier;
    }

    public void run(double w1, double w2, double w3, double w4) throws InterruptedException {


        model_x += clip(((radius / 4.0) * (w1 + w2 + w3 + w4)) * multiplier, -1.0, 1.0) * ((double) loopTime / 1000.0);
        model_y += clip((((radius / 4.0) * (w1 + w4)) + (((radius / 4.0) * -1.0) * (w2 + w3))) * multiplier, -1.0, 1.0) * ((double) loopTime / 1000.0);
        model_theta += clip(((radius / 4.0) * ((w2 + w4 - w1 - w3) / (wheelBaseX + wheelbaseY))) * multiplier, -1.0, 1.0) * ((double) loopTime / 1000.0);

        super.run();
    }

    public void run(double x, double y, double w) throws InterruptedException {

        double w1 = (1.0 / radius) * (x + y + (w * (-1.0 * (wheelBaseX + wheelbaseY))));
        double w2 = (1.0 / radius) * (x - y + (w * (wheelBaseX + wheelbaseY)));
        double w3 = (1.0 / radius) * (x - y + (w * (-1.0 * (wheelBaseX + wheelbaseY))));
        double w4 = (1.0 / radius) * (x + y + (w * (wheelBaseX + wheelbaseY)));

        run(w1, w2, w3, w4);

        super.run();
    }

}
