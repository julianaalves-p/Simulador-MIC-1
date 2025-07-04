public class MainMemory {
    private final static short MAIN_MEMORY_SIZE = 4096;
    private short[] memory = new short[MAIN_MEMORY_SIZE];

    /* Inicializa memory com todos os espaços zerados */
    public MainMemory() {
        for (int i = 0; i < MAIN_MEMORY_SIZE; i++) { 
            memory[i] = 0;                               
        }
    }
    /* Lê um dado da memória utilizando o endereço do MAR e coloca no MBR */
    public void readFromMemory(Register MAR, Register MBR) { 
        short readResult = memory[MAR.get()];
        MBR.set(readResult);
    }
    /* Escreve um dado na memória no endereço do MAR e com o valor do MBR */
    public void writeToMemory(Register MAR, Register MBR) {
        this.memory[MAR.get()] = MBR.get();
    }
    public void setManual(int addr, int value) {
        memory[(short)addr] = (short)value;
    }
    public short getManual(int addr) { //
        return memory[(short)addr]; //
    }
}
