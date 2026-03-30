package trigonometry.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import testing.lab2.trigonometry.Cosecant;

@ExtendWith(MockitoExtension.class)
public class CosecantIntegrationTest {

    private static final double PRECISION = 1e-9;

    private Cosecant csc = new Cosecant();

    @BeforeEach
    void init() {
        csc = new Cosecant();
    }

    @ParameterizedTest(name = "csc({0}) throws")
    @ValueSource(doubles = { 0, Math.PI })
    void testCscDomainExceptions(double x) {
        assertThrows(ArithmeticException.class, () -> csc.calculate(x, PRECISION));
    }

    @ParameterizedTest(name = "csc({0})")
    @CsvFileSource(resources = "/csc.csv", numLinesToSkip = 1, delimiter = ';')
    void testCscValues(double x, double y) {
        assertEquals(y, csc.calculate(x, PRECISION), PRECISION);
    }
}
