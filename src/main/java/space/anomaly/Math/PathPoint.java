package space.anomaly.Math;

public class PathPoint {
    public double x;
    public double y;
    public double speed;
    public double turnSpeed;

    public PathPoint() {}

    public PathPoint(double x, double y, double speed, double turnSpeed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.turnSpeed = turnSpeed;
    }

    public Point toPoint(){
        return new Point(this.x, this.y);
    }

}
