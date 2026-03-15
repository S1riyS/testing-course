package testing.lab2.system;

import testing.lab2.function.AbstractMathFunction;
import testing.lab2.logarithm.BaseNLogarithm;
import testing.lab2.logarithm.NaturalLogarithm;
import testing.lab2.trigonometry.Cosecant;

public class EquationSystem extends AbstractMathFunction {
    private final Cosecant csc;

    private final NaturalLogarithm ln;
    private final BaseNLogarithm log2;
    private final BaseNLogarithm log3;
    private final BaseNLogarithm log5;

    public EquationSystem() {
        super();
        this.csc = new Cosecant();

        this.ln = new NaturalLogarithm();
        this.log2 = new BaseNLogarithm(2, this.ln);
        this.log3 = new BaseNLogarithm(3, this.ln);
        this.log5 = new BaseNLogarithm(5, this.ln);
    }

    @Override
    public double calculate(final double x, final double precision) {
        validatePrecision(precision);

        // x <= 0 : csc(x)
        if (x <= 0) {
            return csc.calculate(x, precision);
        }

        // x > 0 : (((((log_5(x) ^ 3) + log_5(x)) - log_3(x)) * ln(x)) - ((log_2(x) * (log_2(x) + ln(x))) / ((log_5(x) - (log_3(x) ^ 2)) ^ 2)))
        final double lnx = ln.calculate(x, precision);
        final double log2x = log2.calculate(x, precision);
        final double log3x = log3.calculate(x, precision);
        final double log5x = log5.calculate(x, precision);

        var lhs = ((Math.pow(log5x, 3) + log5x) - log3x) * lnx;

        var numerator  = log2x * (log2x + lnx);
        var denominator = Math.pow(log5x - Math.pow(log3x, 2), 2);

        if (Math.abs(denominator) < precision) {
            throw new ArithmeticException(String.format("Знаменатель равен нулю при x = %s", x));
        }

        return lhs - (numerator / denominator);
    }
}
