package test_ext;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import testing.lab2.logarithm.BaseNLogarithm;
import testing.lab2.logarithm.NaturalLogarithm;
import testing.lab2.system.EquationSystem;
import testing.lab2.trigonometry.Cosecant;

@ExtendWith(MockitoExtension.class)
public class Step1_RealTrigTest extends AbstractEquationSystemTest {

    @Mock
    NaturalLogarithm mockLn;
    @Mock
    BaseNLogarithm mockLog2, mockLog3, mockLog5;

    @Override
    protected EquationSystem createSystem(double x) {
        if (x > 0) {
            Mockito.when(mockLn.calculate(x, PRECISION)).thenReturn(Math.log(x));
            Mockito.when(mockLog2.calculate(x, PRECISION)).thenReturn(Math.log(x) / Math.log(2));
            Mockito.when(mockLog3.calculate(x, PRECISION)).thenReturn(Math.log(x) / Math.log(3));
            Mockito.when(mockLog5.calculate(x, PRECISION)).thenReturn(Math.log(x) / Math.log(5));
        }
        return new EquationSystem(new Cosecant(), mockLn, mockLog2, mockLog3, mockLog5);
    }
}