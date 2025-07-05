import java.io.File;

public class Simulation {
    public static void main(String[] args) {
        CPU cpu = new CPU();

        loadTest_02_v2(cpu);
        short inst;
        inst = Assembler.assembleAndLoad("LOCO 10");
        System.out.println(inst);
        inst = Assembler.assembleAndLoad("ADDD 20");
        System.out.println(inst);
        inst = Assembler.assembleAndLoad("STOD 30");
        System.out.println(inst);

        // cpu.run();

        // int value;
        // for (int i = 0; i < 30; i++) {
        //     value = cpu.MP.getManual(i);
        //     System.out.printf("MP[%d]: %d\n", i, value);
        // }
        // for (int i = 999; i < 1009; i++) {
        //     value = cpu.MP.getManual(i);
        //     System.out.printf("MP[%d]: %d\n", i, value);
        // }
        // for (int i = 1999; i < 2009; i++) {
        //     value = cpu.MP.getManual(i);
        //     System.out.printf("MP[%d]: %d\n", i, value);
        // }
        // for (int i = 4085; i < 4096; i++) {
        //     value = cpu.MP.getManual(i);
        //     System.out.printf("MP[%d]: %d\n", i, value);
        // }
    }

    public static void loadTest_01(CPU cpu) {
        cpu.MP.add(28677);   // LOCO 5
        cpu.MP.add(4196);    // STOD 100
        cpu.MP.add(28674);   // LOCO 2
        cpu.MP.add(4206);    // STOD 110
        cpu.MP.add(28682);   // LOCO 10
        cpu.MP.add(8292);    // ADDD 100
        cpu.MP.add(4216);    // STOD 120
        cpu.MP.add(100);     // LODD 100
        cpu.MP.add(8302);    // ADDD 110
        cpu.MP.add(-3072);   // PUSH
        cpu.MP.add(120);     // LODD 120
        cpu.MP.add(12388);   // SUBD 100
        cpu.MP.add(-3072);   // PUSH
        cpu.MP.add(-1);      // HALT
    }

    public static void loadTest_02(CPU cpu) {
        // --- Configuração dos Dados Iniciais ---
        // Valores das variáveis que o seu programa irá manipular
        cpu.MP.setManual(1000, 3);    // i = 3
        cpu.MP.setManual(1001, 5);    // n = 5
        cpu.MP.setManual(1002, 2);    // y = 2
        cpu.MP.setManual(2003, 50);   // x[i] (x[3]) = 50

        // --- Carregamento do Programa na Memória ---
        // A numeração de endereços começa em 0
        cpu.MP.add(28675);   // 0: LOCO 3
        cpu.MP.add(5096);    // 1: STOD 1000
        cpu.MP.add(28677);   // 2: LOCO 5
        cpu.MP.add(5097);    // 3: STOD 1001
        cpu.MP.add(28674);   // 4: LOCO 2
        cpu.MP.add(5098);    // 5: STOD 1002
        cpu.MP.add(28722);   // 6: LOCO 50
        cpu.MP.add(6099);    // 7: STOD 2003

        // Início do programa principal (a partir do endereço 8)
        cpu.MP.add(1001);    // 8: LODD 1001 (n)
        cpu.MP.add(13288);   // 9: SUBD 1000 (i)
        cpu.MP.add(-16353);  // 10: JNEG 31 (endereço de 'saida')

        // Calcula 8*y
        cpu.MP.add(1002);    // 11: LODD 1002 (y)
        cpu.MP.add(-3072);   // 12: PUSH
        cpu.MP.add(-24576);  // 13: ADDL 0
        cpu.MP.add(-28672);  // 14: STOL 0
        cpu.MP.add(-24576);  // 15: ADDL 0
        cpu.MP.add(-28672);  // 16: STOL 0
        cpu.MP.add(-24576);  // 17: ADDL 0
        cpu.MP.add(-3072);   // 18: PUSH (resultado 8y)

        // Calcula &x[i]
        cpu.MP.add(30672);   // 19: LOCO 2000 (endereço base de x)
        cpu.MP.add(9192);    // 20: ADDD 1000 (i)
        cpu.MP.add(-3072);   // 21: PUSH (&x[i])

        // Pega o valor de x[i]
        cpu.MP.add(-4096);   // 22: PSHI

        // Calcula o novo valor
        cpu.MP.add(-2560);   // 23: POP (valor de x[i] para AC)
        cpu.MP.add(-20480);  // 24: SUBL 0 (AC = AC - 8y)
        cpu.MP.add(-3072);   // 25: PUSH (resultado)

        // Armazena o novo valor
        cpu.MP.add(-32767);  // 26: LODL 1 (endereço &x[i] para AC)
        cpu.MP.add(-3584);   // 27: POPI (armazena resultado em m[&x[i]])

        // Limpa a pilha
        cpu.MP.add(-1022);   // 28: INSP 2

        // saida: (no endereço 29)
        cpu.MP.add(-1);      // 29: HALT
    }
    public static void loadTest_02_v2(CPU cpu) {
        // --- Configuração dos Dados Iniciais ---
        // Valores das variáveis que o seu programa irá manipular
        cpu.MP.setManual(1000, 3);    // i = 3
        cpu.MP.setManual(1001, 5);    // n = 5
        cpu.MP.setManual(1002, 2);    // y = 2
        cpu.MP.setManual(2003, 50);   // x[i] (x[3]) = 50
        
        // --- Carregamento do Programa na Memória ---
        // A numeração de endereços começa em 0
        cpu.MP.add(28675);   // 0: LOCO 3
        cpu.MP.add(5096);    // 1: STOD 1000
        cpu.MP.add(28677);   // 2: LOCO 5
        cpu.MP.add(5097);    // 3: STOD 1001
        cpu.MP.add(28674);   // 4: LOCO 2
        cpu.MP.add(5098);    // 5: STOD 1002
        cpu.MP.add(28722);   // 6: LOCO 50
        cpu.MP.add(6099);    // 7: STOD 2003
        
        // Início do programa principal (a partir do endereço 8)
        cpu.MP.add(1000);    // 8: LODD 1000 (i)
        cpu.MP.add(13289);   // 9: SUBD 1001 (n) - calcula i - n
        cpu.MP.add(-16358);  // 10: JPOS 29 - CORRIGIDO: usa JPOS em vez de JNEG
        
        // Calcula 8*y
        cpu.MP.add(1002);    // 11: LODD 1002 (y)
        cpu.MP.add(-3072);   // 12: PUSH
        cpu.MP.add(-24576);  // 13: ADDL 0
        cpu.MP.add(-28672);  // 14: STOL 0
        cpu.MP.add(-24576);  // 15: ADDL 0
        cpu.MP.add(-28672);  // 16: STOL 0
        cpu.MP.add(-24576);  // 17: ADDL 0
        cpu.MP.add(-3072);   // 18: PUSH (resultado 8y)
        
        // Calcula &x[i]
        cpu.MP.add(30672);   // 19: LOCO 2000 (endereço base de x)
        cpu.MP.add(9192);    // 20: ADDD 1000 (i)
        cpu.MP.add(-3072);   // 21: PUSH (&x[i])
        
        // Pega o valor de x[i]
        cpu.MP.add(-4096);   // 22: PSHI
        
        // Calcula o novo valor
        cpu.MP.add(-2560);   // 23: POP (valor de x[i] para AC)
        cpu.MP.add(-20480);  // 24: SUBL 0 (AC = AC - 8y)
        cpu.MP.add(-3072);   // 25: PUSH (resultado)
        
        // Armazena o novo valor
        cpu.MP.add(-32767);  // 26: LODL 1 (endereço &x[i] para AC)
        cpu.MP.add(-3584);   // 27: POPI (armazena resultado em m[&x[i]])
        
        // Limpa a pilha
        cpu.MP.add(-1022);   // 28: INSP 2
        
        // saida: (no endereço 29)
        cpu.MP.add(-1);      // 29: HALT

    }
}
