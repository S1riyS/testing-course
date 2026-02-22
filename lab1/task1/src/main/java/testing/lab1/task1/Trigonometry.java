package testing.lab1.task1;

public final class Trigonometry {

    private static final double EPS = Double.MIN_VALUE;

    public static double cos(double x) {
        return cos(x, Integer.MAX_VALUE);
    }

    public static double cos(double x, int n) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            return Double.NaN;
        }

        x = reduceAngle(x);
        return calculateSeries(x, n);
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

    private static double calculateSeries(double x, int maxTerms) {
        double sum = 1.0;
        double term = 1.0;

        for (int n = 1; n <= maxTerms; n++) {
            term *= -x * x / ((2 * n - 1) * (2 * n));
            sum += term;

            if (Math.abs(term) <= EPS)
                break;
        }

        return sum;
    }
}
