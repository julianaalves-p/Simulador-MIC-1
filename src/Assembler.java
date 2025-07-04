import java.util.List;
import java.util.Map;

public class Assembler {

    
    public void assembleAndLoad(String assemblyCode, MainMemory memory, int startAddress) {

        if (assemblyCode == null || assemblyCode.trim().isEmpty()) {
            System.out.println("Nenhum código assembly fornecido para montagem.");
            return;
        }

        int currentAddress = startAddress; // end da MP onde a instrução será armazenada

        for (String line : assemblyCode.trim().split("\n")) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("//") || line.startsWith("#")) {
                continue;
            }

            String[] parts = line.replace(",", "").trim().split("\\s+");
            String mnemonic = parts[0].toUpperCase();

            if (!Dicio.inst.containsKey(mnemonic)) {
                throw new IllegalArgumentException("Instrução desconhecida: " + mnemonic + " na linha: " + line);
            }

            String binaryPrefix = Dicio.inst.get(mnemonic);
            StringBuilder fullBinaryInstructionBuilder = new StringBuilder(binaryPrefix);

            
            if (mnemonic.equals("LOAD") || mnemonic.equals("STOD") || mnemonic.equals("ADDD") ||
                mnemonic.equals("SUBD") || mnemonic.equals("JPOS") || mnemonic.equals("JZER") ||
                mnemonic.equals("JUMP") || mnemonic.equals("LOCO") || mnemonic.equals("LODL") ||
                mnemonic.equals("STOL") || mnemonic.equals("ADDL") || mnemonic.equals("SUBL") ||
                mnemonic.equals("JNEG") || mnemonic.equals("JNZE")) {
                if (parts.length < 2) {
                    throw new IllegalArgumentException("Instrução '" + mnemonic + "' requer um operando de endereço na linha: " + line);
                }
                try {
                    int address = Integer.parseInt(parts[1]);
                    if (address < 0 || address > 4095) {
                        throw new IllegalArgumentException("Endereço fora do range de 12 bits (0-4095) para '" + mnemonic + "' na linha: " + line);
                    }
                    String binaryAddress = String.format("%12s", Integer.toBinaryString(address)).replace(' ', '0');
                    fullBinaryInstructionBuilder.append(binaryAddress);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Operando inválido para '" + mnemonic + "'. Esperado um número, encontrado: " + parts[1] + " na linha: " + line);
                }
            } else if (mnemonic.equals("INSP") || mnemonic.equals("DESP")) {
                if (parts.length < 2) {
                    throw new IllegalArgumentException("Instrução '" + mnemonic + "' requer um operando de valor (y) na linha: " + line);
                }
                try {
                    int value = Integer.parseInt(parts[1]);
                    if (value < 0 || value > 255) {
                        throw new IllegalArgumentException("Valor fora do range de 8 bits (0-255) para '" + mnemonic + "' na linha: " + line);
                    }
                    String binaryValue = String.format("%8s", Integer.toBinaryString(value)).replace(' ', '0');
                    fullBinaryInstructionBuilder.append(binaryValue);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Operando inválido para '" + mnemonic + "'. Esperado um número, encontrado: " + parts[1] + " na linha: " + line);
                }
            } else {
                if (parts.length > 1) {
                    throw new IllegalArgumentException("Instrução '" + mnemonic + "' não espera operandos, mas foi encontrado: " + parts[1] + " na linha: " + line);
                }
            }

        
            String binaryInstructionString = fullBinaryInstructionBuilder.toString();

            // instruções da ISA são de 16 bits (4 bits opcode + 12 bits end)
            // ou 15 bits (7 bits de opcode + 8 bits de valor).
            
            // Para as instruções INSP e DESP (7 bits + 8 bits = 15 bits), preenchi com um 0 à esquerda
            // para que a instrução tenha 16 bits e caiba no short.
            if (binaryInstructionString.length() < 16) {
                binaryInstructionString = "0" + binaryInstructionString; // Garante 16 bits para INSP/DESP
            }

            short instructionValue = (short) Integer.parseInt(binaryInstructionString, 2);

            
            memory.setManual(currentAddress, instructionValue);
            System.out.println("Montada e carregada no endereço " + currentAddress + ": " + line + " -> " + binaryInstructionString + " (valor: " + instructionValue + ")");
            currentAddress++; 
        }
    }
}
