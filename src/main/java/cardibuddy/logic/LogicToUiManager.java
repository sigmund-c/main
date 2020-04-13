package cardibuddy.logic;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.testsession.AnswerType;
import cardibuddy.model.testsession.TestResult;
import cardibuddy.ui.UiManager;

/**
 * The LogicToUi manager of the application.
 */
public class LogicToUiManager implements LogicToUi {

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

    public void setNewCommandBox() {
        ui.setNewCommandBox();
    }

    public void updateFlashcardPanel() {
        ui.getMainWindow().updateCards(displayedDeck);
    }

    public void removeFlashcards() {
        ui.getMainWindow().removeFlashcards();
    }

    public void openStatisticPanel() {
        ui.getMainWindow().fillInnerPartsWithStatistic();
    }

    public void openStatisticPanel(int deckIndex) {
        ui.getMainWindow().fillInnerPartsWithStatistic(deckIndex);
    }

    public void openStatisticPanel(int deckIndex, int sessionIndex) {
        ui.getMainWindow().fillInnerPartsWithStatistic(deckIndex, sessionIndex);
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

    public Deck getDisplayedDeck() {
        return displayedDeck;
    }

    public boolean isInDeck() {
        return this.inDeck;
    }

    /**
     * Displays the current question for the ongoing test session.
     * This question is displayed on the left panel.
     * @param question question belonging to the current flashcard being tested
     */
    public void showTestQuestion(Question question, AnswerType answerType) {
        ui.getMainWindow().fillInnerPartsWithQuestion(question, answerType);
    }

    /**
     * Displays the current question for the ongoing test session.
     * This question is displayed on the left panel.
     * Also renders the image.
     */
    public void showTestQuestionWithImage(Question question, AnswerType answerType, String path) {
        ui.getMainWindow().fillInnerPartsWithQuestionAndImage(question, answerType, path);
    }

    /**
     * Displays the current status of the test (How many flashcards left, encouragement).
     * This status is displayed on the right panel.
     * @param testQueueSize the number of flashcards left to test, excluding the current one being tested
     */
    public void showTestStatus(int testQueueSize) {
        ui.getMainWindow().showTestStatus(testQueueSize);
    }

    /**
     * Displays the result of the user's answering of the current flashcard being tested.
     * This result replaces the question being displayed, in the left panel.
     * @param testResult
     */
    public void showTestResult(TestResult testResult) {
        ui.getMainWindow().fillInnerPartsWithResult(testResult);
    }

    /**
     * Displays the main page again.
     * Adds the deck list back to the left panel, but clears the right panel.
     */
    public void showTestEnd() {
        ui.getMainWindow().fillInnerPartsWithDecks();
        ui.getMainWindow().clearFlashcardListPanel();
    }
}

