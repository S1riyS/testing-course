package trigonometry.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
public class CosecantTest {

    private static final double PRECISION = 1e-9;

    @Mock
    private Sine mockSin;

    @ParameterizedTest(name = "csc({0}) throws")
    @ValueSource(doubles = { 0, Math.PI })
    void testCscDomainExceptions(double x) {
        Mockito.when(mockSin.calculate(x, PRECISION)).thenReturn(Math.sin(x));
        Cosecant csc = new Cosecant(mockSin);

        assertThrows(ArithmeticException.class, () -> csc.calculate(x, PRECISION));
    }

    @ParameterizedTest(name = "csc({0})")
    @CsvFileSource(resources = "/csc.csv", numLinesToSkip = 1, delimiter = ';')
    void testCscValues(double x, double y) {
        Mockito.when(mockSin.calculate(x, PRECISION)).thenReturn(Math.sin(x));
        Cosecant csc = new Cosecant(mockSin);

        assertEquals(y, csc.calculate(x, PRECISION), PRECISION);
    }
}
