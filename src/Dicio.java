import java.util.HashMap;
import java.util.Map;

public class Dicio {

    
    // os 'x's indicam um endereço de máquina de 12 bits que será preenchido pelo montador.
    public static final Map<String, String> inst = new HashMap<>();

    // map para operações da ALU
    public static final Map<String, String> aluOperations = new HashMap<>();

    // map para códigos de registradores 
    public static final Map<String, String> registerCodes = new HashMap<>();

    static {
        // 23 instruções da ISA
        inst.put("LODD", "0000");
        inst.put("STOD", "0001"); 
        inst.put("ADDD", "0010"); 
        inst.put("SUBD", "0011"); 
        inst.put("JPOS", "0100"); 
        inst.put("JZER", "0101"); 
        inst.put("JUMP", "0110"); 
        inst.put("LOCO", "0111"); 
        inst.put("LODL", "1000"); 
        inst.put("STOL", "1001"); 
        inst.put("ADDL", "1010"); 
        inst.put("SUBL", "1011"); 
        inst.put("JNEG", "1100"); 
        inst.put("JNZE", "1101"); 
        inst.put("CALL", "1110000000000000"); 
        inst.put("PSHI", "1111000000000000");
        inst.put("POPI", "1111001000000000"); 
        inst.put("PUSH", "1111010000000000");
        inst.put("POPP", "1111011000000000"); 
        inst.put("RETN", "1111100000000000"); 
        inst.put("SWAP", "1111101000000000"); 
        inst.put("INSP", "1111110"); 
        inst.put("DESP", "1111111");
        inst.put("HALT", "1111111111111111");

        // inicialização das operações da ALU (conforme ALU.java)
        aluOperations.put("ADD", "00");
        aluOperations.put("AND", "01");
        aluOperations.put("PASS_A", "10");
        aluOperations.put("INV_A", "11");

        // inicialização dos códigos de registradores (conforme CPU.java)
        registerCodes.put("PC", "0000"); // 0
        registerCodes.put("AC", "0001"); // 1
        registerCodes.put("SP", "0010"); // 2
        registerCodes.put("IR", "0011"); // 3
        registerCodes.put("TIR", "0100"); // 4
        registerCodes.put("0", "0101"); // 5
        registerCodes.put("+1", "0110"); // 6
        registerCodes.put("-1", "0111"); // 7
        registerCodes.put("AMASK", "1000"); // 8
        registerCodes.put("SMASK", "1001"); // 9
        registerCodes.put("A", "1010"); // 10
        registerCodes.put("B", "1011"); // 11
        registerCodes.put("C", "1100"); // 12
        registerCodes.put("D", "1101"); // 13
        registerCodes.put("E", "1110"); // 14
        registerCodes.put("F", "1111"); // 15
    }
} 
