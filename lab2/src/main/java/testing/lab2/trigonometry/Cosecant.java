package testing.lab2.trigonometry;

import testing.lab2.function.AbstractMathFunction;

public class Cosecant extends AbstractMathFunction {

    private final Sine sine;

    public Cosecant() {
        super();
        this.sine = new Sine();
    }

    public Cosecant(final Sine sine) {
        super();
        this.sine = sine;
    }

    @Override
    public double calculate(final double x, final double precision) {
        validatePrecision(precision);

        final double sinValue = sine.calculate(x, precision);

        if (Math.abs(sinValue) < precision) {
            throw new ArithmeticException("csc(x) не определён при sin(x) = 0");
        }

        return 1.0 / sinValue;
    }
}
