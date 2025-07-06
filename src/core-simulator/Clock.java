public class Clock {
    private byte currentSubcycle;
    private int clockCounter;

    public Clock() {
        this.currentSubcycle = 0;
        this.clockCounter = 0;
    }
    public byte get() {
        return currentSubcycle;
    }
    public int getClockCounter() {
        return this.clockCounter;
    }
    public void increment() {
        this.currentSubcycle = (byte) ((this.currentSubcycle + 1) % 4);
    }
    public void incrementCounter() {
        this.clockCounter++;
    }
}
