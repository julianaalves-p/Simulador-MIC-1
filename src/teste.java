import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class teste {
    public static void main(String[] args) {
        CPU cpu = new CPU();
        Assembler assembler = new Assembler();
        MainMemory mainMemory = cpu.MP; // Acesso à memória principal da CPU

        String assemblyProgram = ""; // String para armazenar o conteúdo do arquivo assembly
        String fileName = "macroprogram.txt"; // Nome do arquivo padrão

        // ---- Bloco para ler o programa assembly de um arquivo ----
        try {
            // Verifica se um nome de arquivo foi passado como argumento de linha de comando
            if (args.length > 0) {
                fileName = args[0]; // Usa o primeiro argumento como nome do arquivo
            }
            System.out.println("Lendo programa assembly de '" + fileName + "'...");
            assemblyProgram = Files.readString(Paths.get(fileName)); // Lê todo o conteúdo do arquivo
            System.out.println("Programa assembly carregado com sucesso.");
        } catch (IOException fileReadException) { // Renomeado 'e' para 'fileReadException'
            System.err.println("Erro ao ler o arquivo '" + fileName + "': " + fileReadException.getMessage());
            System.err.println("Certifique-se de que o arquivo existe no diretório do projeto ou forneça o caminho completo.");
            return; // Encerra a execução se o arquivo não puder ser lido
        }
      
        int programLoadAddress = 0; 

        try {
            System.out.println("Iniciando montagem e carregamento do programa na MainMemory...");
            assembler.assembleAndLoad(assemblyProgram, mainMemory, programLoadAddress);
            System.out.println("Montagem e carregamento concluídos.\n");
        } catch (IllegalArgumentException assemblyException) { // Renomeado 'e' para 'assemblyException'
            System.err.println("Erro durante a montagem: " + assemblyException.getMessage());
            return; // Interrompe a simulação se houver erro de montagem
        }

        // Você pode querer inicializar o PC para o endereço onde o programa foi carregado
        cpu.registers[0].set((short) programLoadAddress); // PC = 0, para iniciar a execução do programa

        System.out.println("Clock subcycle: " + cpu.clock.get());

        System.out.println("\nExecutando alguns subciclos da CPU...");
        for (int i = 0; i < 5; i++) { // Executa 5 vezes o primeiro subciclo
            System.out.println("Subciclo " + (cpu.clock.get() + 1) + ":");
            cpu.runFirstSubcycle();
            cpu.registers[3].printValue(); // IR: <valor da instrução lida>
            cpu.registers[0].printValue(); // PC: <valor incrementado>
            System.out.println("---");
        }

        System.out.println("\nVerificando alguns valores na MainMemory após o carregamento:");
        System.out.println("Memória[0]: " + cpu.MP.getManual(0)); // Deveria ser a primeira instrução
        System.out.println("Memória[1]: " + cpu.MP.getManual(1)); // Deveria ser a segunda instrução
        System.out.println("Memória[6]: " + cpu.MP.getManual(6)); // Deveria ser a instrução DESP
    }
}