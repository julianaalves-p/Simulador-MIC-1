public class ALU 
{
    public enum Opcode {ADD, AND, PASS_A, INV_A};
    
    private short result;

    public short execute(Opcode operation, short bus_A, short bus_B)
    {
        result = 0;
        switch (operation) {
            case ADD:
                result = (short) (bus_A + bus_B);
                break;
            case AND:
                result = (short) (bus_A & bus_B);
            case PASS_A:
                result = bus_A;
            case INV_A:
                result = (short) (~bus_A);
            default:
                break;
        }
        return result;
    }
}

/*

*/