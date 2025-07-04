public class CPU {
    public static void main(String[] args) {
        MainMemory MP = new MainMemory();
        Register PC = new Register("Programa Counter Register");
        Register IR = new Register("Instruction Register");
        MP.writeToAddr(0, 30);
        PC.setValue(0);
        IR.setValue(MP.readFromAddr(0));
        System.out.println(IR.getValue());        
    }
}
