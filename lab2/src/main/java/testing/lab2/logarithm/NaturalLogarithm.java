package testing.lab2.logarithm;

import testing.lab2.function.AbstractMathFunction;

public class NaturalLogarithm extends AbstractMathFunction {

    private static final double LN_2 = 0.693147180559945309417232121458176568;

    public NaturalLogarithm() {
        super();
    }

    @Override
    public double calculate(final double a, final double precision) {
        validatePrecision(precision);

        if (a <= 0.0) {
            throw new ArithmeticException("Аргумент натурального логарифма должен быть меньше нуля");
        }

        double m = a;
        int k = 0;

        while (m >= 2.0) {
            m /= 2.0;
            k++;
        }

        while (m < 1.0) {
            m *= 2.0;
            k--;
        }

        final double y = (m - 1.0) / (m + 1.0);
        final double ySquared = y * y;

        double term = y;
        double sum = term;

        int n = 0;
        while (Math.abs(term) > precision && n < seriesLength) {
            term *= ySquared;
            sum += term / (2.0 * n + 3.0);
            n++;
        }

        return k * LN_2 + 2.0 * sum;
    }
}
