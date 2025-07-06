import java.io.File;

public class Simulation {
    public static void main(String[] args) {
        CPU cpu = new CPU();
        cpu.loadProgram("dataFiles/macroprogram.txt");
        cpu.run();
    }
}
