public class MainMemory {
    private final static short MAIN_MEMORY_SIZE = 4096;
    private short[] memory = new short[MAIN_MEMORY_SIZE];
    private short addressLatch;
    private boolean readEnable;
    private boolean writeEnable;

    /* Inicializa memory com todos os espaços zerados */
    public MainMemory() {
        for (int i = 0; i < MAIN_MEMORY_SIZE; i++) { 
            memory[i] = 0;                               
        }
        this.readEnable = false;
        this.writeEnable = false;
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
    public void setManual(int addr, int value) {
        memory[(short)addr] = (short)value;
    }
    public short getManual(int addr) { //
        return memory[(short)addr]; //
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
}
