// import java.util.List;
// import java.util.ArrayList;

public class CPU {

    /* Barramentos A , B e C 
     * Latches A e B
     * Valores do decoder A, B e C
    */
    short busLine_A, busLine_B, busLine_C;
    short latch_A, latch_B;
    byte decodedA, decodedB, decodedC;

    // Registradores, Memória principal, ALU , AMUX e Shifter
    Clock clock;
    Register [] registers;
    Register MAR, MBR, MPC;
    Register32bit MIR;
    MainMemory MP;
    int [] controlMemory;
    ALU alu;
    Amux amux;
    Shifter shifter;

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
        MBR = new Register("MAR", (short)0);
        MPC = new Register("MPC", (short)0);
        MIR = new Register32bit("MIR", 0);
        
        MP = new MainMemory();
        controlMemory = FileParser.getControlMemory();
        alu = new ALU();
        amux = new Amux();
        shifter = new Shifter();
    }
    public void runFirstSubcycle() {
        MIR.set(controlMemory[MPC.get()]);
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

        System.out.println("Current subcycle: " + clock.get());
        System.out.println("Current MPC: " + MPC.get());
        clock.increment();
        MPC.set((short)(MPC.get() + 1));
    }
    public void runSecondSubcycle() {
        decodedA = get_BUSA_Field();
        decodedB = get_BUSB_Field();

        latch_A = registers[decodedA].get();
        latch_B = registers[decodedB].get();

        clock.increment();
    }

    public void runThirdSubcycle() {
        byte controlSignal_AMUX, controlSignal_MAR, controlSignal_ALU, controlSignal_SHIFTER;

        controlSignal_AMUX = get_AMUX_Field();
        controlSignal_MAR = get_MAR_Field();
        controlSignal_ALU = get_ALU_Field();
        controlSignal_SHIFTER = get_SHIFTER_Field();

        amux.decideOutput(controlSignal_AMUX, latch_A, MBR.get());

        if (controlSignal_MAR == 1) {
            MAR.set(latch_B);
        }
        alu.execute(controlSignal_ALU, amux.getOutput(), latch_B);
        shifter.execute(controlSignal_SHIFTER, alu.get());
    }

public void printCPUState() {
    System.out.println("\n---------- CPU STATE ----------");
    System.out.println("--- Clock ---");
    System.out.println("Sub-cycle: " + clock.get());

    System.out.println("\n--- Control Registers ---");
    System.out.println("MPC: " + MPC.get());
    System.out.printf("MIR: (%d)\n", MIR.get()); // Imprime em Hex e Decimal
    System.out.println("MAR: " + MAR.get());
    System.out.println("MBR: " + MBR.get());

    System.out.println("\n--- Internal Latches & Buses ---");
    System.out.println("Latch A: " + latch_A);
    System.out.println("Latch B: " + latch_B);
    System.out.println("Bus C (Shifter Out): " + shifter.getOuput()); // Supondo que Shifter tenha um método get()

    System.out.println("\n--- ALU State ---");
    System.out.println("ALU Out: " + alu.get());

    System.out.println("\n--- Main Registers ---");
    for (int i = 0; i < registers.length; i++) {
        // Imprime em duas colunas para melhor visualização
        System.out.printf("  %-5s: %-6d", registers[i].getName(), registers[i].get());
        if (i % 2 != 0) {
            System.out.println();
        }
    }
    System.out.println("---------------------------------\n");
}

    // Funções de decode dos campos da microinstrução
    public void printMIRFields() {
        System.out.println("Current Microinstruction Fields:");
        System.out.println("ADDR: " + get_ADDR_Field());
        System.out.println("BUS_A: " + get_BUSA_Field());
        System.out.println("BUS_B: " + get_BUSB_Field());
        System.out.println("BUS_C: " + get_BUSC_Field());
        System.out.println("EN_C: " + get_ENC_Field());
        System.out.println("WR: " + get_WR_Field());
        System.out.println("RD: " + get_RD_Field());
        System.out.println("MAR: " + get_MAR_Field());
        System.out.println("MBR: " + get_MBR_Field());
        System.out.println("SHIFTER: " + get_SHIFTER_Field());
        System.out.println("ALU: " + get_ALU_Field());
        System.out.println("COND: " + get_COND_Field());
        System.out.println("AMUX: " + get_AMUX_Field());
    }
    private int getMicroInstructionField(int shift, int mask) {
        int bitField = (MIR.get() >> shift) & mask;
        return bitField;
    }
    public byte get_ADDR_Field() {
        return (byte)getMicroInstructionField(0, 0x00000FF);
    }
    public byte get_BUSA_Field() {
        return (byte)getMicroInstructionField(8, 0x00000F);
    }
    public byte get_BUSB_Field() {
        return (byte)getMicroInstructionField(12, 0x00000F);
    }
    public byte get_BUSC_Field() {
        return (byte)getMicroInstructionField(16, 0x00000F);
    }
    public byte get_ENC_Field() {
        return (byte)getMicroInstructionField(20, 0x000001);
    }
    public byte get_WR_Field() {
        return (byte)getMicroInstructionField(21, 0x000001);
    }
    public byte get_RD_Field() {
        return (byte)getMicroInstructionField(22, 0x000001);
    }
    public byte get_MAR_Field() {
        return (byte)getMicroInstructionField(23, 0x000001);
    }
    public byte get_MBR_Field() {
        return (byte)getMicroInstructionField(24, 0x000001);
    }
    public byte get_SHIFTER_Field() {
        return (byte)getMicroInstructionField(25, 0x000003);
    }
    public byte get_ALU_Field() {
        return (byte)getMicroInstructionField(27, 0x000003);
    }
    public byte get_COND_Field() {
        return (byte)getMicroInstructionField(29, 0x000003);
    }
    public byte get_AMUX_Field() {
        return (byte)getMicroInstructionField(31, 0x000001);
    }
}
