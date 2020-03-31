package cardibuddy.logic;

import cardibuddy.ui.UiManager;

/**
 * Allows the Commands to control the change in view of the MainWindow panel.
 */
public class LogicToUiManager {

    protected UiManager ui;

    public LogicToUiManager(UiManager ui) {
        this.ui = ui;
    }

    public void openFlashcardPanel(int index) {
        ui.getMainWindow().fillInnerPartsWithCards(index);
    }

    public void openStatisticPanel(int index) { // Statistics of a specific deck
        ui.getMainWindow().fillInnerPartsWithStatistic(index);
    }

    public void openStatisticPanel() { // Statistics of all the decks
        ui.getMainWindow().fillInnerPartsWithStatistic();
    }
}
