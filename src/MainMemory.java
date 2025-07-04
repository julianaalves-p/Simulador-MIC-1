public class MainMemory {
    public static short MAIN_MEMORY_SIZE = 4096;
    public short[] MP = new short[MAIN_MEMORY_SIZE];

    /* Inicializa MP com todos os espaços zerados */
    public MainMemory() {
        for (int i = 0; i < MAIN_MEMORY_SIZE; i++) { 
            MP[i] = 0;                               
        }
    }
    /* Lê um dado da memória utilizando o endereço do MAR e coloca no MBR */
    public void readFromMemory(Register MAR, Register MBR) { 
        short readResult = MP[MAR.getValue()];
        MBR.setValue(readResult);
    }
    /* Escreve um dado na memória no endereço do MAR e com o valor do MBR */
    public void writeToMemory(Register MAR, Register MBR) {
        this.MP[MAR.getValue()] = MBR.getValue();
    }
}
