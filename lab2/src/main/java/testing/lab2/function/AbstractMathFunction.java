package testing.lab2.function;

public abstract class AbstractMathFunction implements IMathFunction {
    private static final int MAX_ITERATIONS = 1000;

    protected final int seriesLength;

    protected AbstractMathFunction() {
        this.seriesLength = MAX_ITERATIONS;
    }

    protected void validatePrecision(final double precision) {
        if (precision <= 0 || precision >= 1) {
            throw new ArithmeticException("Точность должна быть между 0 и 1 включительно");
        }
    }
}
