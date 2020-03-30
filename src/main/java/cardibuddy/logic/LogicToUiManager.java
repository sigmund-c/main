package cardibuddy.logic;

import cardibuddy.model.deck.Deck;
import cardibuddy.ui.UiManager;

/**
 * Allows the Commands to control the change in view of the MainWindow panel.
 */
public class LogicToUiManager {

    protected UiManager ui;
    private String openedDeck;
    private Deck displayedDeck;
    private boolean inDeck = false;


    public LogicToUiManager(UiManager ui) {
        this.ui = ui;
    }

    public void openFlashcardPanel(int index) {
        ui.getMainWindow().fillInnerPartsWithCards(index);
    }

    public void openDeckPanel() {
        ui.getMainWindow().fillInnerPartsWithDecks();
    }

    public void setOpenedDeck(Deck openedDeck) {
        if (openedDeck == null) {
            inDeck = false;
            this.openedDeck = "";
        } else {
            inDeck = true;
            this.openedDeck = openedDeck.getTitle().toString().toLowerCase();
        }
        this.displayedDeck = openedDeck;
    }

    public String getOpenedDeck() {
        return openedDeck;
    }

    public boolean isInDeck() {
        return this.inDeck;
    }

}
