package space.anomaly.Math;

public class Matrix {

    private int rows;
    private int cols;

    public double[] vals;

    public Matrix() {}

    public Matrix(int rows, int cols) {

        this.rows = rows;
        this.cols = cols;
    }

    public Matrix(double[] vals, int rows, int cols) {
        this.vals = vals;
        this.rows = rows;
        this.cols = cols;
    }

    public void multiply(double multiplier) {
        for (int i = 0; i < vals.length; i++) {
            vals[i] *= multiplier;
        }
    }

    public static void multiply(Matrix in, double multiplier, Matrix out) {
        try {
            if (!(in.rows == out.rows) || !(in.cols == out.cols)) {
                throw new MatrixOperationException("matrices must be the same size");
            }
        } catch (MatrixOperationException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < in.vals.length; i++) {
            out.vals[i] = in.vals[i] * multiplier;
        }
    }

    public static Matrix multiply(Matrix multiplicand, Matrix multiplier) {
        try {
            if(!(multiplicand.cols == multiplier.rows)) {
                throw new MatrixOperationException("matrices row/column number must match");
            }
        } catch (MatrixOperationException e) {
            e.printStackTrace();
        }
        double[] vals = new double[(multiplicand.cols * multiplier.rows)];
        Matrix m = new Matrix(vals, multiplicand.cols, multiplier.rows);
        for(int i = 0; i < multiplier.rows; i++) {
            for(int j = 0; j < multiplicand.cols; j++) {
                double val = 0;
                for(int k = 0; k < multiplicand.rows; k++) {
                    val += multiplicand.getVal(k + 1, i + 1) * multiplier.getVal(j + 1, k + 1);
                }
//                System.out.print(j + 1);
//                System.out.println(", " + (i + 1));
                m.setVal(j + 1, i + 1, val);
            }
        }

        return m;

    }

    public static Matrix add(Matrix one, Matrix two) {
        //validate inputs
        try {
            if(one.cols != two.cols || one.rows != two.rows) {
                throw new MatrixOperationException("matrices row and column number must match");
            }
        } catch (MatrixOperationException e) {
            e.printStackTrace();
        }

        //initialize data
        double[] vals = new double[(one.rows * one.cols)];
        Matrix sum = new Matrix(vals, one.rows, one.cols);

        //iterate through matrices
        for(int i = 0; i < one.cols; i++) {
            for(int j = 0; j < one.rows; j++) {
                double val = one.getVal(j + 1, i + 1) + two.getVal(j + 1, i + 1);
                sum.setVal(j + 1, i + 1, val);
            }
        }

        return sum;
    }

    public static Matrix subtract(Matrix minuend, Matrix subtrahend) {
        //validate inputs
        try {
            if(minuend.cols != subtrahend.cols || minuend.rows != subtrahend.rows) {
                throw new MatrixOperationException("matrices row and column number must match");
            }
        } catch (MatrixOperationException e) {
            e.printStackTrace();
        }

        //initialize data
        double[] vals = new double[(minuend.rows * minuend.cols)];
        Matrix difference = new Matrix(vals, minuend.rows, minuend.cols);

        //iterate through matrices
        for(int i = 0; i < minuend.cols; i++) {
            for(int j = 0; j < minuend.rows; j++) {
                double val = minuend.getVal(j + 1, i + 1) - subtrahend.getVal(j + 1, i + 1);
                difference.setVal(j + 1, i + 1, val);
            }
        }

        return difference;

    }

    public static double det(Matrix matrix) {
        try {
            if(matrix.rows != matrix.cols) {
                throw new MatrixOperationException("Matrix must be a square matrix!");
            }

        } catch (MatrixOperationException e) {
            e.printStackTrace();
        }
        double[] dets = new double[matrix.rows];

        for(int i = 0; i < matrix.rows; i++) {

        }

        return 1.0;

    }

    public double toDouble() throws MatrixOperationException {
        if(rows != 1 || cols != 1) {
            throw new MatrixOperationException("Must be a 1x1 Matrix to convert to double.");
        }

        return this.getVal(1, 1);
    }

    public double getVal(int row, int col) {
        int index = ((col - 1) * rows) + row;

        try {
            isValid(row, col);
        } catch (MatrixOperationException e) {
            e.printStackTrace();
        }

        return vals[index - 1];
    }

    public void setVal(int row, int col, double val) {
        int index = ((col - 1) * rows) + row;

        try {
            isValid(row, col);
        } catch (MatrixOperationException e) {
            e.printStackTrace();
        }

        vals[index - 1] = val;
    }

    public void setMatrix(Matrix m) {
        this.vals = m.vals;
        this.rows = m.rows;
        this.cols = m.cols;
    }

    public void isValid(double row, double col) throws MatrixOperationException {
        if(col > cols) {
            throw new MatrixOperationException("Cannot get value outside of matrix range.");
        }
        if(row > rows) {
            throw new MatrixOperationException("Cannot get value outside of matrix range.");
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < cols; i++) {
            for(int j = 0; j < rows; j++) {
                s.append(this.getVal(j + 1, i + 1)).append(" | ");
            }
            s.append("\n");
        }

        return s.toString();
    }


}


class MatrixOperationException extends Exception {

    public MatrixOperationException() {
        super("Illegal matrix operation.");
    }

    public MatrixOperationException(String s) {
        super(s);
    }

}
