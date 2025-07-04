public class Amux {
    private short output;

    public Amux() {
        this.output = 0;
    }

    public short getOutput() {
        return output;
    }
    public void decideOutput(byte controlSignal, short input_Latch_A, short input_MBR) {
        if (controlSignal == 1) {
            this.output = input_MBR;
        }
        else {
            this.output = input_Latch_A;
        }
    }
    public void printValue() {
        System.out.println("AMUX: " + output);
    }
}
