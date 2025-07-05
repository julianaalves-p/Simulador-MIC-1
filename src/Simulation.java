import java.io.File;

public class Simulation {
    public static void main(String[] args) {
        CPU cpu = new CPU();

        cpu.MP.setManual(0, 20);
        cpu.MP.setManual(1, 8222);
        cpu.MP.setManual(2, 4136);
        cpu.MP.setManual(3, 28679);
        cpu.MP.setManual(4, 12313);
        cpu.MP.setManual(5, 62976);

        cpu.runFirstSubcycle();
        cpu.runSecondSubcycle();
        cpu.runThirdSubcycle();
        cpu.printCPUState();

        cpu.runFirstSubcycle();
        cpu.runSecondSubcycle();
        cpu.runThirdSubcycle();
        cpu.printCPUState();

        cpu.runFirstSubcycle();
        cpu.runSecondSubcycle();
        cpu.runThirdSubcycle();
        cpu.printCPUState();

        cpu.runFirstSubcycle();
        cpu.runSecondSubcycle();
        cpu.runThirdSubcycle();
        cpu.printCPUState();
    }
}
