package logarithm.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import testing.lab2.logarithm.NaturalLogarithm;

public class NaturalLogarithmTest {

    private static final double PRECISION = 1e-9;

    @ParameterizedTest(name = "ln({0})")
    @CsvFileSource(resources = "/ln.csv", numLinesToSkip = 1, delimiter = ';')
    void testLnValues(double x, double y) {
        NaturalLogarithm ln = new NaturalLogarithm();
        assertEquals(y, ln.calculate(x, PRECISION), PRECISION);
    }

    @ParameterizedTest(name = "ln({0}) throws")
    @ValueSource(doubles = { 0.0, -1.0, -10.0 })
    void testLnDomainExceptions(double x) {
        NaturalLogarithm ln = new NaturalLogarithm();
        assertThrows(ArithmeticException.class, () -> ln.calculate(x, PRECISION));
    }
}

