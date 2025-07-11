    import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileParser {

    public static Map<Short, String> loadMP(MainMemory MP, String filepath) {
        Map<Short, String> instructionTable = new HashMap<>();
        try {
            // Primeiro tenta ler do diretório de recursos
            BufferedReader bf = null;
            try {
                bf = getBufferedReader(filepath);
            } catch (IOException e) {
                // Se não encontrar no recursos, tenta no diretório do projeto
                bf = new BufferedReader(new FileReader(filepath));
            }
            
            String line;
            Assembler assembler = new Assembler();
            
            while ((line = bf.readLine()) != null) {
                line = line.trim();
                
                // Ignora linhas vazias e comentários
                if (line.isEmpty() || line.startsWith("//") || line.startsWith(";")) {
                    continue;
                }
                
                // Remove comentários inline
                int commentIndex = line.indexOf("//");
                if (commentIndex != -1) {
                    line = line.substring(0, commentIndex).trim();
                }
                commentIndex = line.indexOf(";");
                if (commentIndex != -1) {
                    line = line.substring(0, commentIndex).trim();
                }
                
                // Processa a macroinstrução
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    // Verifica se é uma instrução binária ou assembly
                    if (line.matches("[01\\s]+")) {
                        // É uma instrução binária
                        String binary = line.replaceAll("\\s+", "");
                        if (binary.length() > 0) {
                            short machineCode = (short) Integer.parseInt(binary, 2);
                            MP.add(machineCode);
                            System.out.println("Loaded binary: " + binary + " -> " + machineCode);
                        }
                    } else {
                        // É uma instrução assembly
                        short machineCode = assembler.assembleAndLoad(line);
                        MP.add(machineCode);
                        instructionTable.put(machineCode, line);
                        System.out.println("Loaded assembly: " + line + " -> " + machineCode);
                    }
                    
                } catch (Exception e) {
                    System.err.println("Erro ao processar instrução: " + line);
                    System.err.println("Erro: " + e.getMessage());
                }
            }
            
            bf.close();
            System.out.println("Carregamento do arquivo macroprogram.txt concluído.");
            System.out.println();
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo macroprogram.txt: " + e.getMessage());
            System.err.println("Verifique se o arquivo existe no diretório do projeto ou em /dataFiles/");
        }
        return instructionTable;
    }

    public static int[] getControlMemory() {
        int [] output = new int[256];
        String line;
        int index = 0;
        try {
            BufferedReader bf = getBufferedReader("/dataFiles/controlMemory.txt");
            while ((line = bf.readLine()) != null && index < 256) {
                String binary = line.replaceAll("\\s+", "");
                binary = binary.substring(0, 32);
                long converted = Long.parseLong(binary, 2);
                output[index] = (int)converted;
                index++;
            }
        } catch (IOException e) {
            System.err.println("ERRO: Falha ao ler memória de controle.");
            e.printStackTrace();
            System.exit(0);
        }
        return output;
    }

    public static String [] getMicroProgramCode(String filename) {
        List<String> programLines = new ArrayList<>();
        String line;

        try {
            BufferedReader bf = getBufferedReader(filename);

            while ((line = bf.readLine()) != null) {
                programLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Falha ao carregar código.");
            e.printStackTrace();
            return null;
        }
        return programLines.toArray(new String[0]);
    }


    private static BufferedReader getBufferedReader(String resourcePath) throws IOException {
        // Pede ao ClassLoader para encontrar o arquivo como um "stream" de dados
        InputStream inputStream = FileParser.class.getResourceAsStream(resourcePath);

        // Verifica se o arquivo foi realmente encontrado
        if (inputStream == null) {
            throw new IOException("Arquivo de recurso não encontrado: " + resourcePath);
        }

        // Cria um leitor a partir do stream de dados do arquivo
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public static int[] getMacroInstructions(String resourcePath) {

        int[] output = new int[4096];
        String line;
        int index = 0;
        try {
            BufferedReader bf = getBufferedReader(resourcePath);
            while ((line = bf.readLine()) != null && index < 4096) {
                String instruction = line.replaceAll("\\s+", "");
                if (instruction.length() > 0) {
                    output[index] = Integer.parseInt(instruction, 2);
                    index++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
    public static void printInstructionMap(Map<Short, String> map) {
        System.out.println("\n--- Tabela de Macroinstruções Carregadas ---");
        if (map == null || map.isEmpty()) {
            System.out.println("O mapa de instruções está vazio.");
            return;
        }
        for (Map.Entry<Short, String> entry : map.entrySet()) {
            Short machineCode = entry.getKey();
            String assemblyInstruction = entry.getValue();
            System.out.println("  Código [" + machineCode + "] -> Instrução \"" + assemblyInstruction + "\"");
        }
    }
}
