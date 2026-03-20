package testing.lab2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import testing.lab2.export.CsvExportService;
import testing.lab2.function.IMathFunction;
import testing.lab2.logarithm.BaseNLogarithm;
import testing.lab2.logarithm.NaturalLogarithm;
import testing.lab2.system.EquationSystem;
import testing.lab2.trigonometry.Cosecant;
import testing.lab2.trigonometry.Sine;

public class Main {

    private static final double DEFAULT_PRECISION = 1e-9;
    private static final String DEFAULT_OUTPUT = "./output";

    public static void main(String[] args) {
        double start = Double.NaN;
        double stop = Double.NaN;
        double step = Double.NaN;
        double precision = DEFAULT_PRECISION;
        String output = DEFAULT_OUTPUT;
        String functionsArg = "all";

        for (String arg : args) {
            if (arg.startsWith("--start=")) {
                start = Double.parseDouble(arg.substring("--start=".length()));
            } else if (arg.startsWith("--stop=")) {
                stop = Double.parseDouble(arg.substring("--stop=".length()));
            } else if (arg.startsWith("--step=")) {
                step = Double.parseDouble(arg.substring("--step=".length()));
            } else if (arg.startsWith("--precision=")) {
                precision = Double.parseDouble(arg.substring("--precision=".length()));
            } else if (arg.startsWith("--output=")) {
                output = arg.substring("--output=".length());
            } else if (arg.startsWith("--functions=")) {
                functionsArg = arg.substring("--functions=".length());
            }
        }

        if (Double.isNaN(start) || Double.isNaN(stop) || Double.isNaN(step)) {
            printUsage();
            System.exit(1);
        }

        if (step <= 0) {
            System.err.println("Error: --step must be positive");
            System.exit(1);
        }

        Map<String, IMathFunction> available = buildFunctionMap();

        List<String> requested;
        if ("all".equalsIgnoreCase(functionsArg)) {
            requested = List.copyOf(available.keySet());
        } else {
            requested = Arrays.asList(functionsArg.split(","));
            for (String name : requested) {
                if (!available.containsKey(name.trim())) {
                    System.err.println("Unknown function: " + name.trim());
                    System.err.println("Available: " + available.keySet());
                    System.exit(1);
                }
            }
        }

        Path outputDir = Paths.get(output);
        try {
            Files.createDirectories(outputDir);
        } catch (IOException e) {
            System.err.println("Cannot create output directory: " + e.getMessage());
            System.exit(1);
        }

        CsvExportService exporter = new CsvExportService();

        for (String name : requested) {
            String trimmed = name.trim();
            IMathFunction function = available.get(trimmed);
            String filePath = outputDir.resolve(trimmed + ".csv").toString();

            try {
                exporter.export(function, trimmed, start, stop, step, precision, filePath);
                System.out.println("Exported " + trimmed + " -> " + filePath);
            } catch (IOException e) {
                System.err.println("Failed to export " + trimmed + ": " + e.getMessage());
            }
        }
    }

    private static Map<String, IMathFunction> buildFunctionMap() {
        Sine sin = new Sine();
        Cosecant csc = new Cosecant(sin);
        NaturalLogarithm ln = new NaturalLogarithm();
        BaseNLogarithm log2 = new BaseNLogarithm(2, ln);
        BaseNLogarithm log3 = new BaseNLogarithm(3, ln);
        BaseNLogarithm log5 = new BaseNLogarithm(5, ln);
        EquationSystem system = new EquationSystem(csc, ln, log2, log3, log5);

        Map<String, IMathFunction> map = new LinkedHashMap<>();
        map.put("sin", sin);
        map.put("csc", csc);
        map.put("ln", ln);
        map.put("log2", log2);
        map.put("log3", log3);
        map.put("log5", log5);
        map.put("system", system);
        return map;
    }

    private static void printUsage() {
        System.err.println("Usage: java -jar app.jar --start=<start> --stop=<stop> --step=<step> [options]");
        System.err.println();
        System.err.println("Required:");
        System.err.println("  --start=<value>       Start of range");
        System.err.println("  --stop=<value>        End of range");
        System.err.println("  --step=<value>        Step increment (positive)");
        System.err.println();
        System.err.println("Optional:");
        System.err.println("  --precision=<value>   Precision (default: 1e-9)");
        System.err.println("  --output=<dir>        Output directory (default: ./output)");
        System.err.println("  --functions=<list>    Comma-separated: sin,csc,ln,log2,log3,log5,system,all");
        System.err.println("                        (default: all)");
    }
}
