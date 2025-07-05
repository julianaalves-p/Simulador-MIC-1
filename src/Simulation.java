import java.io.File;

public class Simulation {
    public static void main(String[] args) {
        CPU cpu = new CPU();
        cpu.loadProgram("dataFiles/macroprogram.txt");

        cpu.run();

        // Implementar uma funcionalidade de guardar todos os endereços da MP
        // num arquivo de saída, para análise
        int value;
        for (int i = 999; i < 1030; i++) {
            value = cpu.MP.getManual(i);
            System.out.printf("MP[%d]: %d\n", i, value);
        }
        for (int i = 4085; i < 4096; i++) {
            value = cpu.MP.getManual(i);
            System.out.printf("MP[%d]: %d\n", i, value);
        }
    }
}
