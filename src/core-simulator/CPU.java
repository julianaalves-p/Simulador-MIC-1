import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class CPU {
    private Register MAR = new Register("MAR", (short)0);
    private Register MBR = new Register("MBR", (short)0);
    private Register MPC = new Register("MPC", (short)0);
    private Register32bit MIR = new Register32bit("MIR", 0);
    public MainMemory MP;
    /* Barramentos A , B e C 
     * Latches A e B
     * Valores do decoder A, B e C
    */
    private short busLine_A, busLine_B, busLine_C;
    private short latch_A, latch_B;
    private byte decodedA, decodedB, decodedC;

    // Registradores, Memória principal, ALU , AMUX e Shifter
    private Clock clock;
    private Register [] registers;
    private int [] controlMemory;
    private ALU alu;
    private Amux amux;
    private Shifter shifter;

    private String currentMacroInst;
    private String currentMicroInst;
    public String [] microprogramCode = FileParser.getMicroProgramCode("dataFiles/microprogram.txt");
    public Map<Short, String> macroInstructionTable;
    private PrintWriter logWriter;

    public CPU() {
        clock = new Clock();
        registers = new Register[16];

        registers[0] = new Register("PC", (short) 0);
        registers[1] = new Register("AC", (short) 0);
        registers[2] = new Register("SP", (short) 4096);
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

        MP = new MainMemory();
        controlMemory = FileParser.getControlMemory();
        alu = new ALU();
        amux = new Amux();
        shifter = new Shifter();
    }

    public void loadProgram(String filepath) {
        macroInstructionTable = FileParser.loadMP(MP, filepath);
    }
    
    public void runFirstSubcycle() {
        MIR.set(controlMemory[MPC.get()]);
        if (MP.isReadEnabled()) {
            MP.readFromMemory(MBR);;
        }
        else if (MP.isWriteEnabled()) {
            MP.writeToMemory(MBR);
        }

        clock.increment();
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
        shifter.execute(controlSignal_SHIFTER, alu.getOutput());
        clock.increment();
    }
    public void runFourthSubcycle() {
        byte controlSignal_RD, controlSignal_WR, controlSignal_ENC, controlSignal_MBR;
        byte decodedC = get_BUSC_Field();
        controlSignal_ENC = get_ENC_Field();
        controlSignal_RD = get_RD_Field();
        controlSignal_WR = get_WR_Field();
        controlSignal_MBR = get_MBR_Field();

        busLine_C = shifter.getOuput();
        if (controlSignal_MBR == 1) {
            MBR.set(busLine_C);
        }
        if (controlSignal_ENC == 1) {
            registers[decodedC].set(busLine_C);
        }
        if (controlSignal_RD == 1) {
            MP.enableRead(MAR);
        }
        if (controlSignal_WR == 1) {
            MP.enableWrite(MAR);
        }

        calculateNextMPC();
        clock.increment();
    }

    public void nextMicro(){
        runFirstSubcycle();
        runSecondSubcycle();
        runThirdSubcycle();
        runFourthSubcycle();
    }

    public void nextMacro(){
        boolean sameInstruction = true;
        short startCurrentMacroCode = registers[3].get();

        while(sameInstruction){
            nextMicro();
            if(this.registers[3].get() != startCurrentMacroCode){
                sameInstruction = false;
            }
        }
    }

    public void run() {
        boolean running = true;
        startLog("C:\\Users\\caiop\\Desktop\\Caio\\Faculdade\\projetos\\Simulador-MIC-1\\src\\core-simulator\\dataFiles\\EXECUTION_LOG.txt");
        saveCPUState();
        while (running) {
            runFirstSubcycle();
            runSecondSubcycle();
            runThirdSubcycle();
            runFourthSubcycle();
            clock.incrementCounter();
            saveCPUState();
            if (registers[3].get() == -1) {
                running = false;
            }
        }
        endLog();
        MP.createMemoryLog("C:\\Users\\alefe\\IdeaProjects\\Simulador-MIC-1\\src\\core-simulator\\dataFiles\\MEMORY_LOG.txt");
    }

    public void calculateNextMPC() {
        byte controlSignal_COND = get_COND_Field();
        byte addr = get_ADDR_Field();
        boolean bitZ = alu.getZbit();
        boolean bitN = alu.getNbit();
        short nextMPC = (short) (MPC.get() + 1);

        switch (controlSignal_COND) {
            case 0b01:
                if(bitN) {
                    nextMPC = addr;
                }
                break;
                
            case 0b10:
                if(bitZ) {
                    nextMPC = addr;
                }
                break;

            case 0b11:
                nextMPC = addr;
                break;

            default:
                break;
        }
        MPC.set(nextMPC);
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

    // LOG FUNCTIONS
  
    private void startLog(String filename) {
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(filename));
            this.logWriter = new PrintWriter(bfw);
            System.out.println("Log iniciado.");
            this.logWriter.println("#----- CPU Execution Log -----#");
            this.logWriter.println();
        } catch (IOException e) {
            System.out.println("Falha ao criar log.");
            this.logWriter = null;
        }
    }
    
    private void endLog() {
        if (this.logWriter != null) {
            this.logWriter.close();
            System.out.println("Log finalizado.");
        }
    }

    private void saveCPUState() {
        currentMacroInst = getMacroinstCode();
        currentMicroInst = getMicroisntCode();
        if (this.logWriter == null) {
            return;
        }
        this.logWriter.printf("< --- CLOCK CYCLE %d --- >\n", clock.getClockCounter());
        this.logWriter.println("CURRENT MICROINST.: " + currentMicroInst);
        this.logWriter.println("CURRENT MACROINST.: " + currentMacroInst);
        this.logWriter.println();
        this.logWriter.println("< Control Registers >");
        this.logWriter.println("MIR: " + MIR.get());
        this.logWriter.println("MPC: " + MPC.get());
        this.logWriter.println("MAR: " + MAR.get());
        this.logWriter.println("MBR: " + MBR.get());
        this.logWriter.println();
        this.logWriter.println("< Main Registers >");
        for (int i = 0; i < registers.length; i++) {
            logWriter.printf("  %-5s: %-6d", registers[i].getName(), registers[i].get());
            if (i % 2 != 0) {
                logWriter.println();
            }
        }
        this.logWriter.println("#-----------------------------------------#");
        this.logWriter.println();
    }

    private String getMicroisntCode() {
        return this.microprogramCode[MPC.get()];
    }

    private String getMacroinstCode() {
        return macroInstructionTable.getOrDefault(registers[3].get(), "UNKNOWN");    
    }
        
    public void printCPUState() {

    System.out.println("\n---------- CPU STATE ----------");
    System.out.printf("--- Clock %d ---\n", clock.getClockCounter());
    System.out.println("Sub-cycle: " + clock.get());

    System.out.println("\n--- Control Registers ---");
    System.out.println("MPC: " + MPC.get());
    System.out.printf("MIR: (%d)\n", MIR.get());
    System.out.println("MAR: " + MAR.get());
    System.out.println("MBR: " + MBR.get());

    System.out.println("\n--- Internal Latches & Buses ---");
    System.out.println("Latch A: " + latch_A);
    System.out.println("Latch B: " + latch_B);
    System.out.println("Bus C (Shifter Out): " + shifter.getOuput());

    System.out.println("\n--- ALU State ---");
    System.out.println("ALU Out: " + alu.getOutput());

    System.out.println("\n--- Main Registers ---");
    for (int i = 0; i < registers.length; i++) {
        System.out.printf("  %-5s: %-6d", registers[i].getName(), registers[i].get());
        if (i % 2 != 0) {
            System.out.println();
        }
    }

    // Você pode decidir se quer os campos do MIR no arquivo de log também.
    // printMIRFieldsToFile(System.out); 

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

    public void printControlMemory() {
        for (int i = 0; i < 256; i++) {
            System.out.println(controlMemory[i]);
        }
    }

    public String getCurrentMacroInst(){
        return this.currentMacroInst;
    }

    public String getCurrentMicroInst(){
        return this.currentMicroInst;
    }

    public Register[] getRegister(){
        return this.registers;
    }

    public Register getMAR(){
        return this.MAR;
    }

    public Register getMBR(){
        return this.MBR;
    }

    public Register getMPC(){
        return this.MPC;
    }

    public Register32bit getMIR(){
        return this.MIR;
    }
}
