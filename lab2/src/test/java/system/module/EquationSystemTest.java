package system.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import testing.lab2.logarithm.BaseNLogarithm;
import testing.lab2.logarithm.NaturalLogarithm;
import testing.lab2.system.EquationSystem;
import testing.lab2.trigonometry.Cosecant;

@ExtendWith(MockitoExtension.class)
public class EquationSystemTest {

    private static final double PRECISION = 1e-9;
    private static final double DELTA = 1e-6;

    @Mock
    private Cosecant mockCsc;
    @Mock
    private NaturalLogarithm mockLn;
    @Mock
    private BaseNLogarithm mockLog2;
    @Mock
    private BaseNLogarithm mockLog3;
    @Mock
    private BaseNLogarithm mockLog5;

    @ParameterizedTest(name = "f({0})")
    @CsvFileSource(resources = "/equation_system.csv", numLinesToSkip = 1, delimiter = ';')
    void testEquationSystemValues(double x, double expected, boolean isBoundary) {
        setupMocks(x);
        EquationSystem system = new EquationSystem(mockCsc, mockLn, mockLog2, mockLog3, mockLog5);
        assertEquals(expected, system.calculate(x, PRECISION), DELTA);
    }

    @ParameterizedTest(name = "f({0}) throws (csc domain)")
    @ValueSource(doubles = { -Math.PI, 0.0 })
    void testCscDomainExceptions(double x) {
        Mockito.when(mockCsc.calculate(x, PRECISION))
                .thenThrow(new ArithmeticException("csc not defined"));
        EquationSystem system = new EquationSystem(mockCsc, mockLn, mockLog2, mockLog3, mockLog5);
        assertThrows(ArithmeticException.class, () -> system.calculate(x, PRECISION));
    }

    @ParameterizedTest(name = "f({0}) throws (zero denominator)")
    @ValueSource(doubles = { 1.0 })
    void testZeroDenominatorExceptions(double x) {
        setupMocks(x);
        EquationSystem system = new EquationSystem(mockCsc, mockLn, mockLog2, mockLog3, mockLog5);
        assertThrows(ArithmeticException.class, () -> system.calculate(x, PRECISION));
    }

    private void setupMocks(double x) {
        if (x <= 0) {
            Mockito.when(mockCsc.calculate(x, PRECISION)).thenReturn(1.0 / Math.sin(x));
        } else {
            Mockito.when(mockLn.calculate(x, PRECISION)).thenReturn(Math.log(x));
            Mockito.when(mockLog2.calculate(x, PRECISION)).thenReturn(Math.log(x) / Math.log(2));
            Mockito.when(mockLog3.calculate(x, PRECISION)).thenReturn(Math.log(x) / Math.log(3));
            Mockito.when(mockLog5.calculate(x, PRECISION)).thenReturn(Math.log(x) / Math.log(5));
        }
    }
}
