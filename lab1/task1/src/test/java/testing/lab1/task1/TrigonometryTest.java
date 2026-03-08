package testing.lab1.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("cos(x) to Tailor series")
class TrigonometryTest {

    private static final double TOLERANCE = 1e-9;

    @Test
    @DisplayName("Special values: NaN and infinities")
    void testNanAndInfinity() {
        assertTrue(Double.isNaN(Trigonometry.cos(Double.NaN)));
        assertTrue(Double.isNaN(Trigonometry.cos(Double.POSITIVE_INFINITY)));
        assertTrue(Double.isNaN(Trigonometry.cos(Double.NEGATIVE_INFINITY)));
    }

    @ParameterizedTest(name = "cos({0}) = {1}")
    @DisplayName("Boundary values")
    @CsvSource({
            "0, 1",
            "1.5707963267948966, 0",
            "3.141592653589793, -1",
            "4.71238898038469, 0",
            "6.283185307179586, 1"
    })
    void testBoundaryValues(double x, double expected) {
        double actual = Trigonometry.cos(x);
        assertEquals(expected, actual, TOLERANCE);
    }

    @ParameterizedTest(name = "cos({0}) = {1}")
    @DisplayName("Equivalence classes and external points")
    @CsvSource({
            "0.7853981633974483, 0.7071067812",
            "2.356194490192345, -0.7071067812",
            "3.9269908169872414, -0.7071067812",
            "5.497787143782138, 0.7071067812",
            "-999, 0.999649853",
            "777, -0.5177182207"
    })
    void testEquivalenceClassesAndExternalPoints(double x, double expected) {
        double actual = Trigonometry.cos(x);
        assertEquals(expected, actual, TOLERANCE);
    }
}
