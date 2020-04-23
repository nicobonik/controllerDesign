package space.anomaly.Math;

import static java.lang.Math.*;

public class MathFunctions {

    public static double clip(double x, double min, double max) {

        if(x < min) {
            return min;
        } else if(x > max) {
            return max;
        } else {
            return x;
        }

    }

    public static double angleWrap(double angle) {

        while(angle < PI * -1.0) {
            angle += 2.0 * PI;
        }

        while (angle > PI) {
            angle -= 2.0 * PI;
        }

        return angle;
    }

    public static double calcDistanceBetweenTwoLines(double wheelBase, double theta, Point robotPos,
                                                     double m1, double b1, double m2, double b2) {

        double error = ((wheelBase * cos(theta) + robotPos.x) - ((b2 - wheelBase - b1) / (m1 - m2))) + ((wheelBase * sin(theta) + robotPos.y) - ((m1 * (b2 - wheelBase - b1) / (m1 - m2)) + b1));

        return error;
    }


}
