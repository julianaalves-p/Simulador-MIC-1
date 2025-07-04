import java.io.File;

public class Simulation {
    public static void main(String[] args) {
        CPU cpu = new CPU();
        for (int cell : cpu.controlMemory) {
            System.out.println(cell);
        }

        cpu.MP.setManual(0, 10);
        cpu.MP.setManual(1, 20);
        cpu.MP.setManual(2, 30);
        cpu.MP.setManual(3, 40);
        cpu.MP.setManual(4, 50);

        System.out.println("Clock subcycle: " + cpu.clock.get());

        cpu.runFirstSubcycle();
        cpu.registers[3].printValue();
        
        System.out.println("Clock subcycle: " + cpu.clock.get());

        cpu.runFirstSubcycle();
        cpu.registers[3].printValue();

        System.out.println("Clock subcycle: " + cpu.clock.get());

        cpu.runFirstSubcycle();
        cpu.registers[3].printValue();

        System.out.println("Clock subcycle: " + cpu.clock.get());

        cpu.runFirstSubcycle();
        cpu.registers[3].printValue();

        System.out.println("Clock subcycle: " + cpu.clock.get());

        cpu.runFirstSubcycle();
        cpu.registers[3].printValue();
    }
}
