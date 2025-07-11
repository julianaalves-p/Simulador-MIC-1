import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainMemory {
    private final static short MAIN_MEMORY_SIZE = 4096;
    private short[] memory = new short[MAIN_MEMORY_SIZE];
    private short addressLatch;
    private boolean readEnable;
    private boolean writeEnable;
    private short manualSetPointer;

    /* Inicializa memory com todos os espaços zerados */
    public MainMemory() {
        for (int i = 0; i < MAIN_MEMORY_SIZE; i++) { 
            memory[i] = 0;                               
        }
        this.readEnable = false;
        this.writeEnable = false;
        this.manualSetPointer = 0;
    }
    /* Lê um dado da memória utilizando o endereço do MAR e coloca no MBR */
    public void readFromMemory(Register MBR) {
        short addr = addressLatch;
        if (addr < 0 || addr > 4095) {
            System.out.println("Memory out of bounds");
            return;
        }
        short readResult = memory[addressLatch];
        MBR.set(readResult);
        this.readEnable = false;
    }
    /* Escreve um dado na memória no endereço do MAR e com o valor do MBR */
    public void writeToMemory(Register MBR) {
        if (addressLatch < 0 || addressLatch > 4095) {
            System.out.println("Memory out of bounds");
            return;
        }
        this.memory[addressLatch] = MBR.get();
        this.writeEnable = false;
    }
    public boolean isReadEnabled() {
        return this.readEnable;
    }
    public boolean isWriteEnabled() {
        return this.writeEnable;
    }
    public void enableRead(Register MAR) {
        this.addressLatch = (short)(MAR.get() & 0x0FFF);
        this.readEnable = true;
    }
    public void enableWrite(Register MAR) {
        this.addressLatch = (short)(MAR.get() & 0x0FFF);;
        this.writeEnable = true;
    }

    // Funções de inicialização manual
    public void setManual(int addr, int value) {
        memory[(short)addr] = (short)value;
    }
    public short getManual(int addr) { //
        return memory[(short)addr]; //
    }
    public void add(int value) {
        memory[manualSetPointer] = (short)value;
        this.manualSetPointer++;
    }
    public void printUsedMemorySpace() {
        for (int i = 0; i <= this.manualSetPointer; i++) {
            System.out.println(memory[i]);
        }
    }
    public void createMemoryLog(String filename) {
        PrintWriter memoryWriter;
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(filename));
            memoryWriter = new PrintWriter(bfw);
            System.out.println("Log da MP iniciado.");
            memoryWriter.println("#----- MAIN MEMORY DUMP -----#");
            memoryWriter.println();

            // Itera por todos os 4096 endereços da memória.
            for (int i = 0; i < MainMemory.MAIN_MEMORY_SIZE; i++) {
                // Pega o valor de cada endereço de memória.
                // (Supondo que você tenha o método getManual() na sua MainMemory)
                
                // Escreve a linha formatada no arquivo.
                memoryWriter.printf("MP[%d]: %d\n", i, memory[(short)i]);
            }
            memoryWriter.close();
            bfw.close();
            System.out.println("Log da memória criado com sucesso.");

        } catch (IOException e) {
            System.err.println("### ERRO GRAVE: Falha ao criar o arquivo de log da memória! ###");
            e.printStackTrace();
        }
    }
    public void clearMemory() {
        for (int i = 0; i < MAIN_MEMORY_SIZE; i++) { 
            memory[i] = 0;                               
        }
        this.manualSetPointer = 0;
    }
}
