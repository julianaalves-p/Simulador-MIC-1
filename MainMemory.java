public class MainMemory {
    public static short MAIN_MEMORY_SIZE = 4096;
    public short[] MP = new short[MAIN_MEMORY_SIZE];

    public short readFromAddr(int addr) {
        return this.MP[(short)addr];
    }
    public void writeToAddr(int addr, int value) {
        this.MP[(short)addr] = (short)value;
    }
}
