package testing.lab1.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("cos(x) to Tailor series")
class TrigonometryTest {

    private static final double TOLERANCE = 1e-5;

    @ParameterizedTest(name = "cos({0}) = {1}")
    @DisplayName("Boundaries")
    @CsvSource({
            "NaN, NaN",
            "Infinity, NaN",
            "-Infinity, NaN",
            "3.141592653589793, -1",
            "-3.141592653589793, -1"
    })
    void testBoundaryValues(double x, double expected) {
        double actual = Trigonometry.cos(x);

        if (Double.isNaN(expected))
            assertTrue(Double.isNaN(actual));
        else
            assertEquals(expected, actual, TOLERANCE);
    }

    @ParameterizedTest(name = "cos({0}) = {1}")
    @DisplayName("Equivalence classes")
    @CsvSource({
            // (-inf; -pi) and (pi; inf)
            "10.0, -0.8390715291",

            // (-pi; pi)
            "0.0, 1.0",
            "-1.0, 0.5403023059",
    })
    void testEquivalenceClasses(double x, double expected) {
        double actual = Trigonometry.cos(x);
        assertEquals(expected, actual, TOLERANCE);
    }
}