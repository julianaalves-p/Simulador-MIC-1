// import java.util.List;
// import java.util.ArrayList;

public class CPU {

    /* Barramentos A , B e C */
    short busLine_A;
    short busLine_B;
    short busLine_C;
    // Registradores, Memória principal e ALU
    Clock clock;
    Register [] registers;
    Register MAR;
    Register MBR;
    MainMemory MP;
    int [] controlMemory;
    ALU alu;

    public CPU() {
        clock = new Clock();
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
        
        MAR = new Register("MAR", (short)0);
        MBR = new Register("MBR", (short)0); // mudei aqui pq tava repetindo o MAR
        
        MP = new MainMemory();
        controlMemory = FileParser.getControlMemory();
        alu = new ALU();
    }
    public void runFirstSubcycle() {
        busLine_B = registers[0].get();
        MAR.set(busLine_B);
        MP.readFromMemory(MAR, MBR);
        busLine_C = MBR.get();
        registers[3].set(busLine_C); // IR := mbr
        busLine_A = registers[0].get();
        busLine_B = registers[6].get();
        alu.execute(ALU.ADD, busLine_B, busLine_A);
        busLine_C = alu.get();
        registers[0].set(busLine_C);
        clock.increment();
    }
    public void runSecondSubcycle() {
        
    }
}
