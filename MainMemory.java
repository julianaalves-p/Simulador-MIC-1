public class MainMemory {
    public static short MAIN_MEMORY_SIZE = 4096;
    public short[] MP = new short[MAIN_MEMORY_SIZE];

    public MainMemory() {
        for (int i = 0; i < MAIN_MEMORY_SIZE; i++) {
            MP[i] = 0;
        }
    }
    public void readFromMemory(Register MAR, Register MBR) {
        short readResult = MP[MAR.getValue()];
        MBR.setValue(readResult);
    }
    public void writeToMemory(Register MAR, Register MBR) {
        this.MP[MAR.getValue()] = MBR.getValue();
    }
}
