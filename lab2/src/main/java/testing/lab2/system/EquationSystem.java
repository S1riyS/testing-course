package testing.lab2.system;

import testing.lab2.function.AbstractMathFunction;
import testing.lab2.logarithm.BaseNLogarithm;
import testing.lab2.logarithm.NaturalLogarithm;
import testing.lab2.trigonometry.Cosecant;

public class EquationSystem extends AbstractMathFunction {
    private final Cosecant cosecant;

    private final NaturalLogarithm ln;
    private final BaseNLogarithm log_2;
    private final BaseNLogarithm log_3;
    private final BaseNLogarithm log_5;

    public EquationSystem() {
        super();
        this.cosecant = new Cosecant();

        this.ln = new NaturalLogarithm();
        this.log_2 = new BaseNLogarithm(2, this.ln);
        this.log_3 = new BaseNLogarithm(3, this.ln);
        this.log_5 = new BaseNLogarithm(5, this.ln);
    }

    @Override
    public double calculate(final double x, final double precision) {
        validatePrecision(precision);

        // x <= 0 : csc(x)
        if (x <= 0) {
            return cosecant.calculate(x, precision);
        }

        // x > 0 : (((((log_5(x) ^ 3) + log_5(x)) - log_3(x)) * ln(x)) - ((log_2(x) * (log_2(x) + ln(x))) / ((log_5(x) - (log_3(x) ^ 2)) ^ 2)))
        final double ln_value = ln.calculate(x, precision);
        final double log_2_value = log_2.calculate(x, precision);
        final double log_3_value = log_3.calculate(x, precision);
        final double log_5_value = log_5.calculate(x, precision);

        var lhs = ((Math.pow(log_5_value, 3) + log_5_value) - log_3_value) * ln_value;

        var numerator  = log_2_value * (log_2_value + ln_value);
        var denominator = Math.pow(log_5_value - Math.pow(log_3_value, 2), 2);
        var rhs = numerator / denominator;

        return lhs - rhs;
    }

}
