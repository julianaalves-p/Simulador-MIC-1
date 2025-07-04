import java.io.File;

public class Simulation {
    public static void main(String[] args) {
        CPU cpu = new CPU();
        for (int cell : cpu.controlMemory) {
            System.out.println(cell);
        }
        cpu.registers[2].printValue();
    }
}
