package space.anomaly.Math;

public class PID {
    public double Kp;
    public double Ki;
    public double Kd;

    private double error, integral, derivative, lastError;

    public PID() {
        Kp = 1.0;
        Ki = 1.0;
        Kd = 1.0;
    }

    public PID(double Kp, double Ki, double Kd) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;

    }


    public double run(double error, double iterationTime) {
        double output = 0.0;

        integral += ((error + lastError) / 2) * iterationTime / 1000;
        derivative = error - lastError;

        output = Kp * error + Ki * integral + Kd * derivative;

        return output;
    }

    public void reset() {
        error = 0;
        lastError = 0;
        integral = 0;
        derivative = 0;
    }

}
