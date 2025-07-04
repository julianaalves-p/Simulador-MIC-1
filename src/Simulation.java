import java.io.File;

public class Simulation {
    public static void main(String[] args) {
        
        /* Barramentos A , B e C */
        short busLine_A;
        short busLine_B;
        short busLine_C;
        // Memória principal e ALU
        int [] controlMemory;
        MainMemory MP = new MainMemory();
        ALU alu = new ALU();
        /* Registradores */ 
        Register PC = new Register("Program Counter Register");
        Register IR = new Register("Instruction Register");
        Register AC = new Register("Acumulator Register");
        Register SP = new Register("Stack Pointer Register");
        Register MAR = new Register("Memory Address Register");
        Register MBR = new Register("Memory Buffer Register");
        Register P_ONE = new Register("Positive One Register");
        P_ONE.setValue((short)1);

        controlMemory = FileParser.getControlMemory();
        for (int inst : controlMemory) {
            System.out.println(inst);
        }    
    }
}
