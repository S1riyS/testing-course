package testing.lab1.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("cos(x) to Tailor series")
class TrigonometryTest {

    private static final double TOLERANCE = 1e-5;

    @ParameterizedTest(name = "cos({0})")
    @DisplayName("Corner values")
    @ValueSource(doubles = {
            -9999.9,
            -Math.PI,
            -Math.PI + Double.MIN_VALUE,
            -0.00000001,
            -0.0,
            0.0,
            0.00000001,
            Math.PI,
            Math.PI + Double.MIN_VALUE,
            9999.9,
            Double.NaN,
            Double.POSITIVE_INFINITY,
            Double.NEGATIVE_INFINITY
    })
    void checkCornerValues(double param) {
        // Expected values are results of stdlib `cos`
        double expected = Math.cos(param);
        assertAll(() -> assertEquals(expected, Trigonometry.cos(param), TOLERANCE));
    }

    @ParameterizedTest(name = "cos({0}) = {1}")
    @DisplayName("[-PI; PI] range")
    @CsvFileSource(resources = "/values.csv", numLinesToSkip = 1, delimiter = ';')
    void checkValuesBetweenMinusPiAndPi(double x, double y) {
        // Expected values are precalculated
        assertAll(() -> assertEquals(y, Trigonometry.cos(x), TOLERANCE));
    }
}
