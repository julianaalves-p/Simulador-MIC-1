// import java.util.List;
// import java.util.ArrayList;

public class CPU {

    /* Barramentos A , B e C */
    short busLine_A;
    short busLine_B;
    short busLine_C;
    // Registradores, Memória principal e ALU
    Register [] registers;
    MainMemory MP;
    int [] controlMemory;
    ALU alu;

    public CPU() {
        registers = new Register[16];

        registers[0] = new Register("PC", (short) 0);
        registers[1] = new Register("AC", (short) 0);
        registers[2] = new Register("SP", (short) 4095);
        registers[3] = new Register("IR", (short) 0);
        registers[4] = new Register("TIR", (short) 0);
        registers[5] = new Register("0", (short) 0);
        registers[6] = new Register("+1", (short) 1);
        registers[7] = new Register("-1", (short) -1);
        registers[8] = new Register("AMASK", (short) 0x0FFF);
        registers[9] = new Register("SMASK", (short) 0x00FF);
        registers[10] = new Register("A", (short) 0);
        registers[11] = new Register("B", (short) 0);
        registers[12] = new Register("C", (short) 0);
        registers[13] = new Register("D", (short) 0);
        registers[14] = new Register("E", (short) 0);
        registers[15] = new Register("F", (short) 0);
        
        Register MAR = new Register("MAR", (short)0);
        Register MBR = new Register("MAR", (short)0);
        
        MP = new MainMemory();
        controlMemory = FileParser.getControlMemory();
        alu = new ALU();
    }    
    
}
