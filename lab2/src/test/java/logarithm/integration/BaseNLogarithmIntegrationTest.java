package logarithm.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import testing.lab2.logarithm.BaseNLogarithm;
import testing.lab2.logarithm.NaturalLogarithm;

@ExtendWith(MockitoExtension.class)
public class BaseNLogarithmIntegrationTest {

    private static final double PRECISION = 1e-9;

    private NaturalLogarithm ln;

    @BeforeEach
    void init() {
        ln = new NaturalLogarithm();
    }

    @ParameterizedTest(name = "log_{0}({1})")
    @CsvFileSource(resources = "/log_base_n.csv", numLinesToSkip = 1, delimiter = ';')
    void testLogBaseNValues(int base, double x, double y) {
        BaseNLogarithm logBaseN = new BaseNLogarithm(base, ln);
        assertEquals(y, logBaseN.calculate(x, PRECISION), PRECISION);
    }

    @ParameterizedTest(name = "log_10({0}) throws")
    @ValueSource(doubles = { 0.0, -1.0, -10.0 })
    void testLogBaseNDomainExceptions(double x) {
        BaseNLogarithm logBaseN = new BaseNLogarithm(10, ln);
        assertThrows(ArithmeticException.class, () -> logBaseN.calculate(x, PRECISION));
    }
}
