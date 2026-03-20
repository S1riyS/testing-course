package logarithm.module;

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

@ExtendWith(MockitoExtension.class)
public class BaseNLogarithmTest {

    private static final double PRECISION = 1e-9;

    @Mock
    private NaturalLogarithm mockLn;

    @ParameterizedTest(name = "log_{0}({1})")
    @CsvFileSource(resources = "/log_base_n.csv", numLinesToSkip = 1, delimiter = ';')
    void testLogBaseNValues(int base, double x, double y) {
        Mockito.when(mockLn.calculate(x, PRECISION)).thenReturn(Math.log(x));
        Mockito.when(mockLn.calculate(base, PRECISION)).thenReturn(Math.log(base));

        BaseNLogarithm logBaseN = new BaseNLogarithm(base, mockLn);
        assertEquals(y, logBaseN.calculate(x, PRECISION), PRECISION);
    }

    @ParameterizedTest(name = "log_10({0}) throws")
    @ValueSource(doubles = { 0.0, -1.0, -10.0 })
    void testLogBaseNDomainExceptions(double x) {
        int base = 10;
        BaseNLogarithm logBaseN = new BaseNLogarithm(base, mockLn);
        assertThrows(ArithmeticException.class, () -> logBaseN.calculate(x, PRECISION));
    }
}
