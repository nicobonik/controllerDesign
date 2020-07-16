package space.anomaly.Math;

import java.util.Arrays;

public class MathTest {

    public static void main(String[] args) {

        Matrix m = new Matrix(new double[] {
                1, 2, 3,
                4, 5, 6,
                7, 8, 9
        }, 3, 3);
        Matrix m2 = new Matrix(new double[] {
                10, 11, 12,
                20, 21, 22,
                30, 31, 32
        }, 3, 3);

        Matrix out = Matrix.add(m, m2);

        System.out.println(out.toString());
    }
}
