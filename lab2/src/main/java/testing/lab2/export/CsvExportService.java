package testing.lab2.export;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import testing.lab2.function.IMathFunction;

public class CsvExportService {

    private static final String DELIMITER = ";";

    public void export(
            final IMathFunction function,
            final String functionName,
            final double start,
            final double stop,
            final double step,
            final double precision,
            final String outputPath) throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("x" + DELIMITER + functionName + "(x)");

            for (double x = start; x <= stop + step / 2.0; x += step) {
                String yStr;
                try {
                    double y = function.calculate(x, precision);
                    yStr = Double.isFinite(y) ? String.valueOf(y) : "NaN";
                } catch (Exception e) {
                    yStr = "NaN";
                }
                writer.println(x + DELIMITER + yStr);
            }
        }
    }
}
