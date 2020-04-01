package cardibuddy.logic;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.testsession.TestResult;
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

    public void openStatisticPanel(int index) { // Statistics of a specific deck
        ui.getMainWindow().fillInnerPartsWithStatistic(index);
    }

    public void openStatisticPanel() { // Statistics of all the decks
        ui.getMainWindow().fillInnerPartsWithStatistic();
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

    public void showTestQuestion(Question question) {
        ui.getMainWindow().fillInnerPartsWithQuestion(question);
    }

    public void showTestAnswer(Answer answer) {
        ui.getMainWindow().fillInnerPartsWithAnswer(answer);
    }

    public void showTestResult(TestResult testResult) {
        ui.getMainWindow().fillInnerPartsWithResult(testResult);
    }

    /**
     * To show the end of test.
     */
    public void showTestEnd() {
        ui.getMainWindow().fillInnerPartsWithDecks();

    }

}
