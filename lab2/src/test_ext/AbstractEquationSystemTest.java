package test_ext;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import testing.lab2.system.EquationSystem;

public abstract class AbstractEquationSystemTest {

    protected static final double PRECISION = 1e-9;
    protected static final double DELTA = 1e-6;

    protected abstract EquationSystem createSystem(double x);

    @ParameterizedTest
    @CsvFileSource(resources = "/equation_system.csv", numLinesToSkip = 1, delimiter = ';')
    void testEquationSystemValues(double x, double expected, boolean isBoundary) {
        assertEquals(expected, createSystem(x).calculate(x, PRECISION), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = { -Math.PI, 0.0 })
    void testCscDomainExceptions(double x) {
        assertThrows(ArithmeticException.class, () -> createSystem(x).calculate(x, PRECISION));
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1.0 })
    void testZeroDenominatorExceptions(double x) {
        assertThrows(ArithmeticException.class, () -> createSystem(x).calculate(x, PRECISION));
    }
}