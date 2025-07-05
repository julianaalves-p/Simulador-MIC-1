import java.io.File;

public class Simulation {
    public static void main(String[] args) {
        CPU cpu = new CPU();

        // Carregando o programa na memória da CPU
        cpu.MP.add(28677);   // LOCO 5
        cpu.MP.add(4196);    // STOD 100
        cpu.MP.add(28674);   // LOCO 2
        cpu.MP.add(4206);    // STOD 110
        cpu.MP.add(28682);   // LOCO 10
        cpu.MP.add(8292);    // ADDD 100
        cpu.MP.add(4216);    // STOD 120
        cpu.MP.add(100);     // LODD 100
        cpu.MP.add(8302);    // ADDD 110
        cpu.MP.add(-3072);   // PUSH
        cpu.MP.add(120);     // LODD 120
        cpu.MP.add(12388);   // SUBD 100
        cpu.MP.add(-3072);   // PUSH
        cpu.MP.add(-1);      // HALT

        cpu.run();

        int value;
        for (int i = 0; i < 130; i++) {
            value = cpu.MP.getManual(i);
            System.out.printf("MP[%d]: %d\n", i, value);
        }
        System.out.println();
        System.out.println("...");
        System.out.println();
        value = cpu.MP.getManual(4093);
        System.out.printf("MP[%d]: %d\n", 4093, value);
        value = cpu.MP.getManual(4094);
        System.out.printf("MP[%d]: %d\n", 4094, value);
        value = cpu.MP.getManual(4095);
        System.out.printf("MP[%d]: %d\n", 4095, value);
    }
}
