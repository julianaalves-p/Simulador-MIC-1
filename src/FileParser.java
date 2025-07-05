    import java.io.*;

    public class FileParser {
        

        public static void loadMP(MainMemory MP) {
            //LER PROXIMA MACROINST. DO ARQUIVO MACROPROGRAM.TXT
            //INTERPRETAR USANDO METODO assembleAndLoad DA CLASSE Assembler
            //Guardar resultado na MP
        }
        public static int[] getControlMemory() {
            int [] output = new int[256];
            String line;
            int index = 0;
            try {
                BufferedReader bf = getBufferedReader("/dataFiles/controlMemory.txt");
                line = bf.readLine(); // consome a primeira linha (estrutura do endereço)
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
    }
