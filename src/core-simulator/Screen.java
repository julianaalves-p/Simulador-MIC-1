import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Screen {
    CPU cpu = new CPU();
    private JFrame screen = new JFrame("Simulation");
    private boolean loaded = false;
    // Micro/Macro innstructions label
    private JLabel macroExibition = new JLabel("Current Macro");
    private JLabel microExibition = new JLabel("Current Micro");
    private JLabel currentMicroLabel = new JLabel();
    private JLabel currentMacroLabel = new JLabel();

    //CPU state labels
    private JLabel programCounterLabel = new JLabel("PC: 0");
    private JLabel acumulatorLabel = new JLabel("AC: 0");
    private JLabel stackPointerLabel = new JLabel("SP: 0");
    private JLabel irLabel = new JLabel("IR: 0");
    private JLabel mpcLabel = new JLabel("MPC: 0");
    private JLabel marLabel = new JLabel("MAR: 0");
    private JLabel mbrLabel = new JLabel("MBR: 0");
    private JLabel mirLabel = new JLabel("MIR: 0");

    public Screen(){

        Cores paleta = new Cores();
        Constantes constantes = new Constantes();
        Font textButtonFont = new Font("Times New Roman", Font.BOLD, constantes.buttonFontSize);
        Font textLabelFont = new Font("Times new Roman", Font.BOLD, constantes.labelFontSize);

        //Screen
        this.screen.setSize(constantes.screenWidth, constantes.screenHght);
        this.screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.screen.setVisible(true);
        this.screen.setResizable(false);
        this.screen.setLocationRelativeTo(null);
        this.screen.setLayout(null);

        //Buttons
        JButton nextMacroButton = new JButton("Next macro");
        this.modifyButton(nextMacroButton, constantes.macroButtoPosX, constantes.macroButtonPosY,
                constantes.buttonsSizeX, constantes.buttonsSizeY, textButtonFont, paleta.branco, paleta.azul);

        JButton nextMicroButton = new JButton("Next micro");
        this.modifyButton(nextMicroButton, constantes.microButtonPosX, constantes.microButtonPosY,
                constantes.buttonsSizeX, constantes.buttonsSizeY, textButtonFont, paleta.branco, paleta.azul);

        JButton clearProgramButton = new JButton("Clear program");
        this.modifyButton(clearProgramButton, constantes.clearButtonPosX, constantes.clearButtonPosY,
                constantes.buttonsSizeX, constantes.buttonsSizeY, textButtonFont, paleta.branco, paleta.azul);

        JButton loadProgramButton = new JButton("Load program");
        this.modifyButton(loadProgramButton, constantes.loadButtonPosX, constantes.loadButtonPosY,
                constantes.buttonsSizeX, constantes.buttonsSizeY, textButtonFont, paleta.branco, paleta.azul);

        JButton runProgramButton = new JButton("Run program");
        this.modifyButton(runProgramButton, constantes.runButtonPosX, constantes.runButtonPosY,
                constantes.buttonsSizeX, constantes.buttonsSizeY, textButtonFont, paleta.branco, paleta.verde);

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

        //Button actions
        nextMacroButton.addActionListener(action -> macroInstruction(action));
        nextMicroButton.addActionListener(action -> nextMicroInstruction(action));
        clearProgramButton.addActionListener(action -> closeProgram(action));
        runProgramButton.addActionListener(action ->runProgram(action));
        loadProgramButton.addActionListener(action->loadingProgram(action));
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

    private void runProgram(ActionEvent actionEvent){
        if (this.loaded) {
            cpu.run();
            atualizarContadores();
        }
    }

    private void closeProgram(ActionEvent actionEvent){
        System.exit(0);
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

    private void loadingProgram(ActionEvent actionEvent){
        this.cpu.loadProgram("dataFiles/macroprogram.txt");
        this.loaded = true;
        this.currentMacroLabel.setText("Programa carregado!");
        this.currentMicroLabel.setText("Pronto para iniciar.");
    }
}