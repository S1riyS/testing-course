package testing.lab2;

import testing.lab2.system.EquationSystem;

public class Main {
    public static void main(String[] args) {
        EquationSystem system = new EquationSystem();

        System.out.println(system.calculate(3, 0.0001));
    }
}
