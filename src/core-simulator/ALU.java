public class ALU 
{   
    private short output;
    private boolean NBit, ZBit;

    /*
    Flags da ALU:
    00 -> A + B
    01 -> A & B
    10 -> A
    11 -> INV(A)
    */
    public static byte ADD = 0b00; 
    public static byte AND = 0b01;
    public static byte PASS_A = 0b10;
    public static byte INV_A = 0b11;

    public void execute(byte operation, short input_A, short input_B)
    {
        this.output = 0;
        switch (operation) {
            case 0b00: // case ADD
                this.output = (short) (input_A + input_B);
                break;
            case 0b01: // case AND
                this.output = (short) (input_A & input_B);
                break;
            case 0b10: // case PASS_A
                this.output = input_A;
                break;
            case 0b11: // case INV(A)
                this.output = (short) (~input_A);
                break;
            default:
                break;
        }
        if (this.output == 0) {
            this.ZBit = true;
            this.NBit = false;
        }
        else if (this.output < 0) {
            this.NBit = true;
            this.ZBit = false;
        }
        else {
            this.NBit = false;
            this.ZBit = false;
        }
    }

    public short getOutput() {
        return this.output;
    }
    public boolean getNbit() {
        return this.NBit;
    }
    public boolean getZbit() {
        return this.ZBit;
    }
}
