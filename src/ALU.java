public class ALU 
{   
    private short result;

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

    public void execute(byte operation, short bus_A, short bus_B)
    {
        this.result = 0;
        switch (operation) {
            case 0b00: // case ADD
                this.result = (short) (bus_A + bus_B);
                break;
            case 0b01: // case AND
                this.result = (short) (result & bus_B);
                break;
            case 0b10: // case PASS_A
                this.result = bus_A;
                break;
            case 0b11: // case INV(A)
                this.result = (short) (~bus_A);
                break;
            default:
                break;
        }
    }

    public short getValue() {
        return this.result;
    }
}
