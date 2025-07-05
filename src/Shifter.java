public class Shifter {
    private short output;

    public Shifter() {
        this.output = 0;
    }

    public void execute(byte controlSignal, short input) {
        if (controlSignal == 0b00) {
            this.output = input;
        }
        else if (controlSignal == 0b01) {
            this.output = (short)(input >> 1);
        }
        else if (controlSignal == 0b10) {
            this.output = (short)(input << 1);
        }
    }
    public short getOuput() {
        return this.output;
    }
}
