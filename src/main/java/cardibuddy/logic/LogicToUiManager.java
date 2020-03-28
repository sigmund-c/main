package cardibuddy.logic;

import cardibuddy.ui.UiManager;

public class LogicToUiManager {

    protected UiManager ui;

    public LogicToUiManager(UiManager ui) {
        this.ui = ui;
    }

    public void openFlashcardPanel(int index) {
        ui.getMainWindow().fillInnerPartsWithCards(index);
    }

}
