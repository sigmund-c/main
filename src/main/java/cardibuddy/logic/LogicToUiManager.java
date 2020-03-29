package cardibuddy.logic;

import cardibuddy.model.flashcard.Flashcard;
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

    public void openTestingFlashcard(Flashcard flashcard) {
        ui.getMainWindow().fillInnerPartWithUnflippedCard(flashcard);
    }

}
