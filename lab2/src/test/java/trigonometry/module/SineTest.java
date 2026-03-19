package trigonometry.module;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import testing.lab2.trigonometry.Sine;

public class SineTest {

    private static final double PRECISION = 1e-9;

    private Sine sin;

    @BeforeEach
    void init() {
        sin = new Sine();
    }

    @ParameterizedTest(name = "sin({0})")
    @CsvFileSource(resources = "/sin.csv", numLinesToSkip = 1, delimiter = ';')
    void testSineValues(double x, double y) {
        assertEquals(y, sin.calculate(x, PRECISION), PRECISION);
    }
}
