package trigonometry.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import testing.lab2.trigonometry.Cosecant;
import testing.lab2.trigonometry.Sine;

@ExtendWith(MockitoExtension.class)
public class CosecantIntegrationTest {

    private static final double PRECISION = 1e-9;

    private Sine sin;

    @BeforeEach
    void init() {
        sin = new Sine();
    }

    @ParameterizedTest(name = "csc({0}) throws")
    @ValueSource(doubles = { 0, Math.PI })
    void testCscDomainExceptions(double x) {
        Cosecant csc = new Cosecant(sin);
        assertThrows(ArithmeticException.class, () -> csc.calculate(x, PRECISION));
    }

    @ParameterizedTest(name = "csc({0})")
    @CsvFileSource(resources = "/csc.csv", numLinesToSkip = 1, delimiter = ';')
    void testCscValues(double x, double y) {
        Cosecant csc = new Cosecant(sin);
        assertEquals(y, csc.calculate(x, PRECISION), PRECISION);
    }
}
