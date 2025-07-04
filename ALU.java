public class ALU 
{   
    private short result;

    public static byte ADD = 0b00;
    public static byte AND = 0b01;
    public static byte PASS_A = 0b10;
    public static byte INV_A = 0b11;

    public void execute(byte operation, short bus_A, short bus_B)
    {
        this.result = 0;
        switch (operation) {
            case 0b00:
                this.result = (short) (bus_A + bus_B);
                break;
            case 0b01:
                this.result = (short) (result & bus_B);
                break;
            case 0b10:
                this.result = bus_A;
                break;
            case 0b11:
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
