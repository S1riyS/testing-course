package testing.lab2.trigonometry;

import testing.lab2.function.AbstractMathFunction;

public class Sine extends AbstractMathFunction {

    private static final double TWO_PI = 2 * Math.PI;

    public Sine() {
        super();
    }

    @Override
    public double calculate(final double x, final double precision) {
        validatePrecision(precision);

        final double normalizedX = reduceAngle(x);

        double term = normalizedX;
        double sum = term;

        int n = 0;
        while (Math.abs(term) > precision && n < seriesLength) {
            final double factor = -normalizedX * normalizedX / ((2.0 * n + 2.0) * (2.0 * n + 3.0));
            term *= factor;
            sum += term;
            n++;
        }

        return sum;
    }

    private double reduceAngle(final double x) {
        if (Double.isInfinite(x) || Double.isNaN(x)) {
            return Double.NaN;
        }

        double result = x % TWO_PI;

        if (result > Math.PI) {
            result -= TWO_PI;
        } else if (result < -Math.PI) {
            result += TWO_PI;
        }

        return result;
    }
}
