import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import Dicio;

public class Assembler {
    public List<String> assemble(String assemblyCode) {
      
        List<String> binaryInst = new ArrayList<>();

        if (assemblyCode == null || assemblyCode.trim().isEmpty()) {
            return binaryInst; // Return empty list if no code is provided
        }

        for (String line : assemblyCode.trim().split("\n")) {
            String[] parts = line.replace(",", "").trim().split("\\s+");
            if (parts.length == 0 || parts[0].isEmpty()) {
                continue;
            }

            String symbol = parts[0];

            if (!Dicio.inst.containsKey(symbol)) {
                throw new IllegalArgumentException("Instrução desconhecida: " + symbol);
            }

            String binaryPrefix = Dicio.inst.get(symbol);
            
            StringBuilder binary = new StringBuilder(binaryPrefix);
            for (int i = 1; i < parts.length; i++) {
                binary.append(" ").append(parts[i]);
            }
            binaryInst.add(binary.toString());
        }
        return binaryInst;
    }
}
