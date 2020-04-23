package space.anomaly.Math;

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
}
