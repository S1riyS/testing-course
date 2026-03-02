package testing.lab1.task1;

public final class Trigonometry {
    public static double cos(double x) {
        return cos(x, Double.MIN_VALUE);
    }

    public static double cos(double x, double eps) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            return Double.NaN;
        }

        x = reduceAngle(x);
        return calculateSeries(x, eps);
    }

    private static double reduceAngle(double x) {
        if (x > Math.PI || x < -Math.PI) {
            x = x % (2 * Math.PI);
            if (x > Math.PI)
                x -= 2 * Math.PI;
            if (x < -Math.PI)
                x += 2 * Math.PI;
        }
        return x;
    }

    private static double calculateSeries(double x, double eps) {
        double sum = 1.0;
        double term = 1.0;
        int n = 1;

        while (Math.abs(term) > eps) {
            term *= -x * x / ((2.0 * n - 1) * (2.0 * n));
            sum += term;
            n++;
        }

        return sum;
    }
}
