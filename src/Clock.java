public class Clock {
    private byte currentSubcycle;

    public Clock() {
        this.currentSubcycle = 0;
    }
    public byte get() {
        return currentSubcycle;
    }
    public void increment() {
        this.currentSubcycle = (byte) ((this.currentSubcycle + 1) % 4);
    }
}
