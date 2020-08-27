package space.anomaly.Math;

import java.util.ArrayList;

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

        while(angle < -PI) {
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

    public static ArrayList<Point> lineCircleIntersect(Point linePoint1, Point linePoint2, double radius, Point circleCenter) {
        ArrayList<Point> intersections = new ArrayList<Point>();

        double m = (linePoint2.y - linePoint1.y) / (linePoint2.x - linePoint1.x);
        double b = linePoint1.y - (m * linePoint1.x);

        double quadraticA = pow(m, 2) + 1;

        double quadraticB = 2.0 * ((m * b) - (m * circleCenter.y) - circleCenter.x);

        double quadraticC = pow(circleCenter.y, 2) - pow(radius, 2) + pow(circleCenter.x, 2) - (2.0 * b * circleCenter.y) + pow(b, 2);

        try {
            double x1 = (-quadraticB + Math.sqrt(Math.pow(quadraticB, 2) - (4.0 * quadraticA * quadraticC))) / (2.0 * quadraticA);
            double y1 = (m * x1) + b;

            double minX = Math.min(linePoint1.x, linePoint2.x);
            double maxX = Math.max(linePoint1.x, linePoint2.x);

            if(x1 < maxX) {
                intersections.add(new Point(x1, y1));
            }

            double x2 = (-quadraticB - Math.sqrt(Math.pow(quadraticB, 2) - (4.0 * quadraticA * quadraticC))) / (2.0 * quadraticA);
            double y2 = (m * x2) + b;

            if( x2 < maxX) {
                intersections.add(new Point(x2, y2));
            }

        } catch (Exception ignored) {}

        return intersections;

    }

    public static ArrayList<Point> lineCircleIntersect(Point linePoint1, Point linePoint2, double radius, Point circleCenter, boolean minBox, boolean maxBox) {
        ArrayList<Point> intersections = new ArrayList<Point>();

        double m = (linePoint2.y - linePoint1.y) / (linePoint2.x - linePoint1.x);
        double b = linePoint1.y - (m * linePoint1.x);

        double quadraticA = pow(m, 2) + 1;

        double quadraticB = 2.0 * ((m * b) - (m * circleCenter.y) - circleCenter.x);

        double quadraticC = pow(circleCenter.y, 2) - pow(radius, 2) + pow(circleCenter.x, 2) - (2.0 * b * circleCenter.y) + pow(b, 2);

        try {
            double x1 = (-quadraticB + Math.sqrt(Math.pow(quadraticB, 2) - (4.0 * quadraticA * quadraticC))) / (2.0 * quadraticA);
            double y1 = (m * x1) + b;

            double minX = Math.min(linePoint1.x, linePoint2.x);
            double maxX = Math.max(linePoint1.x, linePoint2.x);

            if(minBox && maxBox) {
                if (x1 > minX && x1 < maxX) {
                    intersections.add(new Point(x1, y1));
                }
            }
            else if(maxBox) {
                if (x1 < maxX) {
                    intersections.add(new Point(x1, y1));
                }
            }
            else if(minBox) {
                if (x1 > minX) {
                    intersections.add(new Point(x1, y1));
                }
            }

            double x2 = (-quadraticB - Math.sqrt(Math.pow(quadraticB, 2) - (4.0 * quadraticA * quadraticC))) / (2.0 * quadraticA);
            double y2 = (m * x2) + b;

            if(minBox && maxBox) {
                if (x2 > minX && x2 < maxX) {
                    intersections.add(new Point(x2, y2));
                }
            }
            else if(maxBox) {
                if (x2 < maxX) {
                    intersections.add(new Point(x2, y2));
                }
            }
            else if(minBox) {
                if (x2 > minX) {
                    intersections.add(new Point(x2, y2));
                }
            }

        } catch (Exception ignored) {}

        return intersections;

    }

    public static ArrayList<Point> lineCircleIntersectNoBoundingBox(Point linePoint1, Point linePoint2, double radius, Point circleCenter) {
        ArrayList<Point> intersections = new ArrayList<>();


        double m = (linePoint2.y - linePoint1.y) / (linePoint2.x - linePoint1.x);
        double b = linePoint1.y - (m * linePoint1.x);

        double quadraticA = pow(m, 2) + 1;

        double quadraticB = 2.0 * ((m * b) - (m * circleCenter.y) - circleCenter.x);

        double quadraticC = pow(circleCenter.y, 2) - pow(radius, 2) + pow(circleCenter.x, 2) - (2.0 * b * circleCenter.y) + pow(b, 2);

        try {
            double x1 = (-quadraticB + Math.sqrt(Math.pow(quadraticB, 2) - (4.0 * quadraticA * quadraticC))) / (2.0 * quadraticA);
            double y1 = (m * x1) + b;

            double minX = Math.min(linePoint1.x, linePoint2.x);
            double maxX = Math.max(linePoint1.x, linePoint2.x);

            if(linePoint2.x < linePoint1.x && linePoint2.y < linePoint1.y) {
                if(x1 < maxX) {
                    intersections.add(new Point(x1, y1));
                }
            } else {
                if(x1 > minX) {
                    intersections.add(new Point(x1, y1));
                }
            }

            double x2 = (-quadraticB - Math.sqrt(Math.pow(quadraticB, 2) - (4.0 * quadraticA * quadraticC))) / (2.0 * quadraticA);
            double y2 = (m * x2) + b;

            if(linePoint2.x < linePoint1.x && linePoint2.y < linePoint1.y) {
                if(x2 < maxX) {
                    intersections.add(new Point(x2, y2));
                }
            } else {
                if(x2 > minX) {
                    intersections.add(new Point(x2, y2));
                }
            }

        } catch (Exception ignored) {}

        return intersections;
    }


}
