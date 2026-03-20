package system.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import testing.lab2.system.EquationSystem;

@ExtendWith(MockitoExtension.class)
public class EquationSystemIntegrationTest {

    private static final double PRECISION = 1e-9;

    private EquationSystem system;

    @BeforeEach
    void init() {
        system = new EquationSystem();
    }

    @ParameterizedTest(name = "f({0})")
    @CsvFileSource(resources = "/equation_system.csv", numLinesToSkip = 1, delimiter = ';')
    void testEquationSystemValues(double x, double expected, boolean isBoundary) {
        assertEquals(expected, system.calculate(x, PRECISION), PRECISION);
    }

    @ParameterizedTest(name = "f({0}) throws (csc domain)")
    @ValueSource(doubles = { -Math.PI, 0.0 })
    void testEquationSystemDomainExceptions(double x) {
        assertThrows(ArithmeticException.class, () -> system.calculate(x, PRECISION));
    }

    @ParameterizedTest(name = "f({0}) throws (zero denominator)")
    @ValueSource(doubles = { 1.0 })
    void testZeroDenominatorExceptions(double x) {
        assertThrows(ArithmeticException.class, () -> system.calculate(x, PRECISION));
    }
}
