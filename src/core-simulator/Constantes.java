public class Constantes {
    //Screen
    int screenWidth = 900, screenHght = 720;

    //Fonts
    int buttonFontSize = 22, labelFontSize = 20;

    //Buttons
    int buttonsSizeX = 140, buttonsSizeY = 60;
    int buttonStartY = 580;
    int buttonSpacingX = 160;

    // Posições dos botões (organizados na parte inferior)
    int nextMicroButtonPosX = 30, nextMicroButtonPosY = buttonStartY;
    int nextMacroButtonPosX = nextMicroButtonPosX + buttonSpacingX;
    int nextMacroButtonPosY = buttonStartY;
    int clearButtonPosX = nextMacroButtonPosX + buttonSpacingX;
    int clearButtonPosY = buttonStartY;
    int loadButtonPosX = clearButtonPosX + buttonSpacingX;
    int loadButtonPosY = buttonStartY;
    int runProgramPosX = 30, runProgramPosY = 490;
    int runLocalButtonPosX = 190, runLocalButtonPosY = 490;


    //Labels: Macro and micro exibition
    int labelsSizeX = 300, labelsSizeY = 30;
    int macroLabelPosX = 560, macroLabelPosY = 330;
    int microLabelPosX = 560, microLabelPosY = 400;
    int currentMicroLabelPosX = 560, currentMicroLabelPosY = 420;
    int currentMacroLabelPosX = 560, currentMacroLabelPosY = 350;

    //Labels: CPU State (organizados em colunas)
    int labelYIncrement = 40;
    int cpuStateCol1X = 580;
    int cpuStateCol2X = 730;
    int cpuStateStartY = 60;

    // Coluna 1
    int programCounterLabelPosX = cpuStateCol1X , programCounterLabelPoxY = cpuStateStartY;
    int acumulatorLabelPosX = cpuStateCol1X, acumulatorLabelPosY = cpuStateStartY + labelYIncrement;
    int stackPointerLabelPosX = cpuStateCol1X, stackPointerLabelPosY = cpuStateStartY + (2 * labelYIncrement);
    int irLabelPosX = cpuStateCol1X, irLabelPoxY = cpuStateStartY + (3 * labelYIncrement);

    // Coluna 2
    int mpcLabelPosX = cpuStateCol2X, mpcLabelPosY = cpuStateStartY;
    int marLabelPosX = cpuStateCol2X, marLabelPosY = cpuStateStartY + labelYIncrement;
    int mbrLabelPosX = cpuStateCol2X, mbrLabelPosY = cpuStateStartY + (2 * labelYIncrement);
    int mirLabelPosX = cpuStateCol2X, mirLabelPosY = cpuStateStartY + (3 * labelYIncrement);

    // Subcycle Label
    int subcycleLabelPosX = cpuStateCol1X, subcycleLabelPosY = cpuStateStartY - 40;


    //Main Label (Text Area)
    int textFieldSizeX = 520, textFieldSizeY = 420;
    int textFieldPosx = 25, textFieldPosY = 25;
}