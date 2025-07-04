public class CPU {

    public static void main(String[] args) {
        short busLine_A;
        short busLine_B;
        short busLine_C;
        MainMemory MP = new MainMemory();
        ALU alu = new ALU();

        //Registradores 
        Register PC = new Register("Program Counter Register");
        Register IR = new Register("Instruction Register");
        Register AC = new Register("Acumulator Register");
        Register MAR = new Register("Memory Address Register");
        Register MBR = new Register("Memory Buffer Register");
        Register P_ONE = new Register("Positive One Register");
        P_ONE.setValue((short)1);
        
        // write
        MAR.setValue((short)10);
        MBR.setValue((short)2);
        MP.writeToMemory(MAR, MBR);

        MAR.setValue((short)20);
        MBR.setValue((short)12);
        MP.writeToMemory(MAR, MBR);

        MAR.setValue((short)30);
        MBR.setValue((short)7);
        MP.writeToMemory(MAR, MBR);
    
        MAR.setValue((short)40);
        MBR.setValue((short)89);
        MP.writeToMemory(MAR, MBR);

        MAR.setValue((short)50);
        MBR.setValue((short)101);
        MP.writeToMemory(MAR, MBR);

        // read
        MAR.setValue((short)10);
        MP.readFromMemory(MAR, MBR);
        busLine_C = MBR.getValue();
        IR.setValue(busLine_C);
        IR.printValue();

        MAR.setValue((short)30);
        MP.readFromMemory(MAR, MBR);
        busLine_C = MBR.getValue();
        IR.setValue(busLine_C);
        IR.printValue();

        MAR.setValue((short)16);
        MP.readFromMemory(MAR, MBR);
        busLine_C = MBR.getValue();
        IR.setValue(busLine_C);
        IR.printValue();
    }
}
