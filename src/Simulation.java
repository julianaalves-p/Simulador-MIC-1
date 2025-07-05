import java.io.File;

public class Simulation {
    public static void main(String[] args) {
        CPU cpu = new CPU();

        /*
        Testando programa:
        LOCO 10
        ADDD 20 
        STOD 30
        HALT
        */
        cpu.MP.setManual(0, 28682);
        cpu.MP.setManual(1, 8212);
        cpu.MP.setManual(2, 4126);
        cpu.MP.setManual(3, 0xFFFF);
        cpu.MP.setManual(20, 5);

        cpu.run((short)30);

        System.out.println();
        int value;
        for (int i = 0; i < 31; i++) {
            value = cpu.MP.getManual(i);
            System.out.printf("MP[%d]: %d\n", i, value);
        }
    }
}
