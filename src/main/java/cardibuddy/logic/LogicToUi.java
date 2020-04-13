package cardibuddy.logic;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.testsession.AnswerType;
import cardibuddy.model.testsession.TestResult;

/**
 * Allows the Commands to control the change in view of the MainWindow panel.
 */
public interface LogicToUi {

    public void openFlashcardPanel(int index);

    public void setNewCommandBox();

    /**
     * Updates the list of flashcards in the side panel.
     */
    public void updateFlashcardPanel();

    /**
     * Removes flashcards from the list of flashcards in the side panel.
     */
    public void removeFlashcards();

    /**
     * Opens the panel containing the statistics
     */
    public void openStatisticPanel();

    public void openStatisticPanel(int deckIndex);

    public void openStatisticPanel(int deckIndex, int sessionIndex);

    public void openDeckPanel();

    public void setOpenedDeck(Deck openedDeck);

    public Deck getDisplayedDeck();

    public boolean isInDeck();

    /**
     * To show the current question being tested.
     *
     * @param question {@code Question} is the questions being shown.
     */
    public void showTestQuestion(Question question, AnswerType answerType);

    /**
     * To show the status of the of test.
     *
     * @param testQueueSize is the numbers of tests in the queue.
     */
    public void showTestStatus(int testQueueSize);

    /**
     * To show the result of the of test.
     *
     * @param testResult {@code TestResult} is the results of the test.
     */
    public void showTestResult(TestResult testResult);

    /**
     * To show the end of the test.
     */
    public void showTestEnd();
}
