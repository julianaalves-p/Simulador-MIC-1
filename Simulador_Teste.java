

public class Simulador_Teste {
    public static void main(String[] args)
    {
        ALU myalu = new ALU();
        
        short a = 15;
        short b = 20;
        
        short r = myalu.execute(ALU.Opcode.ADD, a, b);
        System.out.printf("r: %d", r);
    }    
}
