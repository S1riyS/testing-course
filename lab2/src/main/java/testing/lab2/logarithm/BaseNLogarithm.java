package testing.lab2.logarithm;

import testing.lab2.function.AbstractMathFunction;

public class BaseNLogarithm extends AbstractMathFunction {

    private final double base;
    private final NaturalLogarithm ln;

    public BaseNLogarithm() {
        this(10.0, new NaturalLogarithm());
    }

    public BaseNLogarithm(final double base) {
        this(base, new NaturalLogarithm());
    }

    public BaseNLogarithm(final double base, final NaturalLogarithm naturalLogarithm) {
        super();
        this.ln = naturalLogarithm;
        this.base = base;
    }

    @Override
    public double calculate(final double x, final double precision) {
        validatePrecision(precision);

        if (x <= 0.0) {
            throw new ArithmeticException("Аргумент логарифма должен быть строго больше нуля");
        }

        final double lnX = ln.calculate(x, precision);
        final double lnBase = ln.calculate(base, precision);
        return lnX / lnBase;
    }
}
