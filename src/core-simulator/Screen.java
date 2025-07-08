import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Screen {
        CPU cpu = new CPU();
        private final JFrame screen = new JFrame("Simulation");
        private boolean loaded = false;
        // Micro/Macro innstructions label
        private final JLabel macroExibition = new JLabel("Current Macro");
        private final JLabel microExibition = new JLabel("Current Micro");
        private final JLabel currentMicroLabel = new JLabel();
        private final JLabel currentMacroLabel = new JLabel();

        //CPU state labels
        private final JLabel programCounterLabel = new JLabel("PC: 0");
        private final JLabel acumulatorLabel = new JLabel("AC: 0");
        private final JLabel stackPointerLabel = new JLabel("SP: 0");
        private final JLabel irLabel = new JLabel("IR: 0");
        private final JLabel mpcLabel = new JLabel("MPC: 0");
        private final JLabel marLabel = new JLabel("MAR: 0");
        private final JLabel mbrLabel = new JLabel("MBR: 0");
        private final JLabel mirLabel = new JLabel("MIR: 0");

        //Text box
        private final JTextArea textBox = new JTextArea("Carregue seu programa aqui...");
        private final JScrollPane textScrollPane = new JScrollPane(textBox);

        // Lista de botões para habilitar/desabilitar
        private final List<JButton> buttons = new ArrayList<>();


        public Screen(){

                Cores paleta = new Cores();
                Constantes constantes = new Constantes();
                Font textButtonFont = new Font("Times New Roman", Font.BOLD, constantes.buttonFontSize);
                Font textLabelFont = new Font("Times new Roman", Font.BOLD, constantes.labelFontSize);

                textBox.setFont(textLabelFont);
                textBox.setLineWrap(true);
                textBox.setWrapStyleWord(true);

                textScrollPane.setBounds(constantes.textFieldPosx, constantes.textFieldPosY, constantes.textFieldSizeX, constantes.textFieldSizeY);

                this.screen.setSize(constantes.screenWidth, constantes.screenHght);
                this.screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                this.screen.setVisible(true);
                this.screen.setResizable(false);
                this.screen.setLocationRelativeTo(null);
                this.screen.setLayout(null);

                //Buttons
                JButton nextMacroButton = new JButton("Next macro");
                this.modifyButton(nextMacroButton, constantes.nextMacroButtonPosX, constantes.nextMacroButtonPosY,
                        constantes.buttonsSizeX, constantes.buttonsSizeY, textButtonFont, paleta.branco, paleta.azul);

                JButton nextMicroButton = new JButton("Next micro");
                this.modifyButton(nextMicroButton, constantes.nextMicroButtonPosX, constantes.nextMicroButtonPosY,
                        constantes.buttonsSizeX, constantes.buttonsSizeY, textButtonFont, paleta.branco, paleta.azul);

                JButton clearProgramButton = new JButton("Clear program");
                this.modifyButton(clearProgramButton, constantes.clearButtonPosX, constantes.clearButtonPosY,
                        constantes.buttonsSizeX, constantes.buttonsSizeY, textButtonFont, paleta.branco, paleta.azul);

                JButton loadProgramButton = new JButton("Load program");
                this.modifyButton(loadProgramButton, constantes.loadButtonPosX, constantes.loadButtonPosY,
                        constantes.buttonsSizeX, constantes.buttonsSizeY, textButtonFont, paleta.branco, paleta.azul);

                JButton runProgramButton = new JButton("Run Program");
                this.modifyButton(runProgramButton, constantes.runProgramPosX, constantes.runProgramPosY,
                        constantes.buttonsSizeX, constantes.buttonsSizeY, textButtonFont, paleta.branco, paleta.verde);


                // Adiciona botões à lista
                buttons.add(nextMacroButton);
                buttons.add(nextMicroButton);
                buttons.add(clearProgramButton);
                buttons.add(loadProgramButton);
                buttons.add(runProgramButton);


                //Labels: Current micro and macro instructions
                this.modifyLabel(macroExibition, constantes.macroLabelPosX, constantes.macroLabelPosY,
                        constantes.labelsSizeX, constantes.labelsSizeY, textLabelFont, paleta.azul);

                this.modifyLabel(microExibition, constantes.microLabelPosX, constantes.microLabelPosY,
                        constantes.labelsSizeX, constantes.labelsSizeY, textLabelFont, paleta.azul);

                this.modifyLabel(currentMicroLabel, constantes.currentMicroLabelPosX, constantes.currentMicroLabelPosY,
                        constantes.labelsSizeX, constantes.labelsSizeY, textLabelFont, paleta.vermelho);

                this.modifyLabel(currentMacroLabel, constantes.currentMacroLabelPosX, constantes.currentMacroLabelPosY,
                        constantes.labelsSizeX, constantes.labelsSizeY, textLabelFont, paleta.vermelho);


                //Labels: CPU state
                this.modifyLabel(programCounterLabel, constantes.programCounterLabelPosX, constantes.programCounterLabelPoxY,
                        constantes.labelsSizeX, constantes.labelsSizeY, textLabelFont, paleta.azul);

                this.modifyLabel(acumulatorLabel, constantes.acumulatorLabelPosX, constantes.acumulatorLabelPosY,
                        constantes.labelsSizeX, constantes.labelsSizeY, textLabelFont, paleta.azul);

                this.modifyLabel(stackPointerLabel, constantes.stackPointerLabelPosX, constantes.stackPointerLabelPosY,
                        constantes.labelsSizeX, constantes.labelsSizeY, textLabelFont, paleta.azul);

                this.modifyLabel(irLabel, constantes.irLabelPosX, constantes.irLabelPoxY, constantes.labelsSizeX,
                        constantes.labelsSizeY, textLabelFont, paleta.azul);

                this.modifyLabel(mpcLabel, constantes.mpcLabelPosX, constantes.mpcLabelPosY, constantes.labelsSizeX,
                        constantes.labelsSizeY, textLabelFont, paleta.azul);

                this.modifyLabel(marLabel, constantes.marLabelPosX, constantes.marLabelPosY, constantes.labelsSizeX,
                        constantes.labelsSizeY, textLabelFont, paleta.azul);

                this.modifyLabel(mbrLabel, constantes.mbrLabelPosX, constantes.mbrLabelPosY, constantes.labelsSizeX,
                        constantes.labelsSizeY, textLabelFont, paleta.azul);

                this.modifyLabel(mirLabel, constantes.mirLabelPosX, constantes.mirLabelPosY, constantes.labelsSizeX,
                        constantes.labelsSizeY, textLabelFont, paleta.azul);

                //Adding to the screen
                this.screen.add(nextMacroButton); this.screen.add(nextMicroButton);
                this.screen.add(clearProgramButton); this.screen.add(loadProgramButton);
                this.screen.add(runProgramButton);

                this.screen.add(macroExibition); this.screen.add(microExibition);
                this.screen.add(currentMicroLabel);this.screen.add(currentMacroLabel);
                this.screen.add(programCounterLabel); this.screen.add(acumulatorLabel);
                this.screen.add(stackPointerLabel); this.screen.add(irLabel);
                this.screen.add(mpcLabel); this.screen.add(marLabel);
                this.screen.add(mbrLabel); this.screen.add(mirLabel);

                this.screen.add(textScrollPane);

                //Button actions
                nextMacroButton.addActionListener(action -> macroInstruction(action));
                nextMicroButton.addActionListener(action -> nextMicroInstruction(action));
                clearProgramButton.addActionListener(action -> clearProgram(action));
                runProgramButton.addActionListener(this::runProgramFromTextBox);
                loadProgramButton.addActionListener(this::loadingProgramFromTextBox);

        }

        private void modifyLabel(JLabel label, int positionX, int positionY, int sizeX,
                                 int sizeY, Font font, Color collorForeground){
                label.setBounds(positionX, positionY, sizeX, sizeY);
                label.setFont(font); label.setForeground(collorForeground);
        }

        private void modifyButton(JButton button, int positionX, int positionY, int sizeX,
                                  int sizeY, Font font, Color collorForeground, Color collorBackground){
                button.setBounds(positionX, positionY, sizeX, sizeY);
                button.setFont(font); button.setForeground(collorForeground);
                button.setBackground(collorBackground);
        }

        private void setButtonsEnabled(boolean enabled) {
                for (JButton button : buttons) {
                        button.setEnabled(enabled);
                }
        }

        private void atualizarContadores(){
                this.programCounterLabel.setText("PC: " + this.cpu.getRegister()[0].get());
                this.acumulatorLabel.setText("AC: " + this.cpu.getRegister()[1].get());
                this.stackPointerLabel.setText("SP: " + this.cpu.getRegister()[2].get());
                this.irLabel.setText("IR: " + this.cpu.getRegister()[3].get());
                this.marLabel.setText("MAR: " + this.cpu.getMAR().get());
                this.mbrLabel.setText("MBR: " + this.cpu.getMBR().get());
                this.mirLabel.setText("MIR: " + this.cpu.getMIR().get());
                this.mpcLabel.setText("MPC: " + this.cpu.getMPC().get());
        }

        private void runProgramFromTextBox(ActionEvent actionEvent) {
                // Se o botão "Run Local" foi clicado, carrega o programa local primeiro
                if (actionEvent.getActionCommand().equals("Run Local")) {
                        cpu.loadProgram("dataFiles/macroprogram.txt");
                        this.loaded = true;
                        this.currentMacroLabel.setText("Programa local carregado!");
                } else {
                        loadingProgramFromTextBox(actionEvent);
                }

                if (this.loaded) {
                        setButtonsEnabled(false); // Desabilita os botões durante a execução

                        SwingWorker<Void, Void> worker = new SwingWorker<>() {
                                @Override
                                protected Void doInBackground() throws Exception {
                                        Consumer<CPU> observer = (cpuState) -> {
                                                publish(); // Envia um sinal para o método process()
                                                try {
                                                        Thread.sleep(200); // Pausa de 0.2 segundos
                                                } catch (InterruptedException e) {
                                                        Thread.currentThread().interrupt();
                                                }
                                        };
                                        cpu.runWithObserver(observer);
                                        return null;
                                }

                                @Override
                                protected void process(List<Void> chunks) {
                                        // Este método roda na Event Dispatch Thread (EDT), seguro para atualizar a UI
                                        atualizarContadores();
                                        currentMacroLabel.setText(cpu.getCurrentMacroInst());
                                        currentMicroLabel.setText(cpu.getCurrentMicroInst());
                                }

                                @Override
                                protected void done() {
                                        // Este método também roda na EDT, após o doInBackground() terminar
                                        setButtonsEnabled(true); // Reabilita os botões
                                        atualizarContadores(); // Garante a exibição do estado final
                                        currentMacroLabel.setText("Execução finalizada.");
                                        currentMicroLabel.setText("");
                                }
                        };

                        worker.execute();
                }
        }


        private void clearProgram(ActionEvent actionEvent){
                this.cpu = new CPU(); // Cria uma nova instância da CPU
                this.cpu.getRegister()[2].set((short)(0));
                atualizarContadores();
                this.currentMacroLabel.setText("");
                this.currentMicroLabel.setText("");
                this.textBox.setText("Carregue seu programa aqui...");
                this.loaded = false;
        }

        private void macroInstruction(ActionEvent actionEvent){
                if(this.loaded) {
                        this.cpu.nextMacro();
                        this.currentMacroLabel.setText("" + this.cpu.getCurrentMacroInst());
                        this.currentMicroLabel.setText("" + this.cpu.getCurrentMicroInst());
                        atualizarContadores();
                }
                else{
                        this.currentMacroLabel.setText("Carregue o programa antes");
                }
        }

        private void nextMicroInstruction(ActionEvent actionEvent){
                if(this.loaded) {
                        this.cpu.nextMicro();
                        this.currentMicroLabel.setText("" + this.cpu.getCurrentMicroInst());
                        this.currentMacroLabel.setText("" + this.cpu.getCurrentMacroInst());
                        atualizarContadores();
                } else {
                        this.currentMicroLabel.setText("Carregue o programa antes");
                }
        }

        private void loadingProgramFromTextBox(ActionEvent actionEvent){
                String programContent = textBox.getText();
                if (programContent.equals("Carregue seu programa aqui...") || programContent.trim().isEmpty()) {
                        this.currentMacroLabel.setText("Nenhum programa na caixa de texto.");
                        this.loaded = false;
                        return;
                }

                try {
                        // Cria um arquivo temporário para o programa da caixa de texto
                        File tempFile = File.createTempFile("macroprogram_temp", ".txt");
                        tempFile.deleteOnExit(); // Garante que o arquivo seja deletado ao sair
                        try (FileWriter writer = new FileWriter(tempFile)) {
                                writer.write(programContent);
                        }
                        cpu.loadProgram(tempFile.getAbsolutePath());
                        this.loaded = true;
                        this.currentMacroLabel.setText("Programa carregado da caixa de texto!");
                        this.currentMicroLabel.setText("Pronto para iniciar.");
                } catch (IOException e) {
                        this.currentMacroLabel.setText("Erro ao carregar o programa.");
                        e.printStackTrace();
                        this.loaded = false;
                }
        }
}