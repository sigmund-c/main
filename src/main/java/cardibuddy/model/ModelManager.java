package cardibuddy.model;

import static cardibuddy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.logging.Logger;

import cardibuddy.commons.core.GuiSettings;
import cardibuddy.commons.core.LogsCenter;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Statistics;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.CardType;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.testsession.AnswerType;
import cardibuddy.model.testsession.TestResult;
import cardibuddy.model.testsession.TestSession;
import cardibuddy.model.testsession.exceptions.AlreadyCorrectException;
import cardibuddy.model.testsession.exceptions.EmptyDeckException;
import cardibuddy.model.testsession.exceptions.EmptyTestQueueException;
import cardibuddy.model.testsession.exceptions.IncorrectAnswerFormatException;
import cardibuddy.model.testsession.exceptions.NoOngoingTestException;
import cardibuddy.model.testsession.exceptions.UnansweredQuestionException;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


/**
 * Represents the in-memory model of the cardibuddy data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(cardibuddy.model.ModelManager.class);

    private final CardiBuddy cardiBuddy;
    private final UserPrefs userPrefs;
    private final FilteredList<Card> filteredFlashcards;
    private final FilteredList<Deck> filteredDecks;
    private final VersionedCardiBuddy versionedCardiBuddy;
    private TestSession testSession;

    /**
     * Initializes a ModelManager with the given cardiBuddy and userPrefs.
     */
    public ModelManager(ReadOnlyCardiBuddy cardiBuddy, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(cardiBuddy, userPrefs);

        logger.fine("Initializing with CardiBuddy: " + cardiBuddy + " and user prefs " + userPrefs);

        versionedCardiBuddy = new VersionedCardiBuddy(cardiBuddy);
        this.cardiBuddy = new CardiBuddy(cardiBuddy);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFlashcards = new FilteredList<Card>(this.versionedCardiBuddy.getFlashcardList());
        filteredDecks = new FilteredList<Deck>(this.versionedCardiBuddy.getDeckList());
    }

    public ModelManager() {
        this(new CardiBuddy(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCardiBuddyFilePath() {
        return userPrefs.getCardiBuddyFilePath();
    }

    @Override
    public void setCardiBuddyFilePath(Path cardiBuddyFilePath) {
        requireNonNull(cardiBuddyFilePath);
        userPrefs.setCardiBuddyFilePath(cardiBuddyFilePath);
    }

    //=========== CardiBuddy ================================================================================

    @Override
    public ReadOnlyCardiBuddy getCardiBuddy() {
        return versionedCardiBuddy;
    }

    @Override
    public void setCardiBuddy(ReadOnlyCardiBuddy cardiBuddy) {
        versionedCardiBuddy.resetData(cardiBuddy);
    }

    @Override
    public boolean hasDeck(Deck deck) {
        requireNonNull(deck);
        return versionedCardiBuddy.hasDeck(deck);
    }

    @Override
    public void deleteDeck(Deck target) {
        versionedCardiBuddy.removeDeck(target);
    }

    @Override
    public void addDeck(Deck deck) {
        versionedCardiBuddy.addDeck(deck);
        updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
    }

    @Override
    public void setDeck(Deck target, Deck editedDeck) {
        versionedCardiBuddy.setDeck(target, editedDeck);
    }

    @Override
    public boolean hasFlashcard(Card flashcard) {
        requireNonNull(flashcard);
        return versionedCardiBuddy.hasFlashcard(flashcard);
    }

    @Override
    public void deleteFlashcard(Card target) {
        versionedCardiBuddy.removeFlashcard(target);
    }

    /**
     * Adds Flashcard to a Deck.
     *
     * @param flashcard new card.
     */
    @Override
    public void addFlashcard(Card flashcard) {
        versionedCardiBuddy.addFlashcard(flashcard);
        updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public void setFlashcard(Card target, Card editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);
        versionedCardiBuddy.setFlashcard(target, editedFlashcard);
    }

    // ======================== TEST SESSION METHODS =========================================================
    /**
     * Gets the number of flashcards left in the {@code testQueue}.
     * This method is used for the countdown.
     */
    @Override
    public int getTestQueueSize() {
        return testSession.getTestQueueSize();
    }

    /**
     * Starts a test session // TODO see how to update the list
     *
     * @param deck the deck to be tested
     */
    @Override
    public Question testDeck(Deck deck) throws EmptyDeckException {
        requireNonNull(deck);
        testSession = new TestSession(deck);
        return testSession.getFirstQuestion();
    }

    /**
     * Gets the next question in the {@code TestSession}
     */
    @Override
    public Question getNextQuestion() throws
            UnansweredQuestionException,
            NoOngoingTestException,
            EmptyTestQueueException {
        try {
            return testSession.getNextQuestion();
        } catch (NullPointerException e) {
            throw new NoOngoingTestException();
        } catch (NoSuchElementException e) {
            throw new EmptyTestQueueException();
        }
    }

    /**
     * Allows the user to skip the current question. Gets the next question in the test queue, if any.
     */
    @Override
    public Question skipQuestion() throws NoOngoingTestException, AlreadyCorrectException, EmptyTestQueueException {
        try {
            return testSession.skipQuestion();
        } catch (NullPointerException e) {
            throw new NoOngoingTestException();
        } catch (NoSuchElementException e) {
            throw new EmptyTestQueueException();
        }
    }

    /**
     * Checks the given answer in the test session
     *
     * @param userAnswer a string representation of the user's answer
     * @return A Result enums that represents the result of the user's answer.
     */
    @Override
    public TestResult submitAnswer(String userAnswer) throws IncorrectAnswerFormatException {
        return testSession.submitAnswer(userAnswer);
    }

    /**
     * Marks the user's answer as correct when it was marked wrong by the {@code TestSession}.
     * Allows for flexibility in the user's answers.
     */
    @Override
    public void forceCorrect() throws UnansweredQuestionException, AlreadyCorrectException {
        testSession.forceCorrect();
    }

    /**
     * Clears the current {@code TestSession}.
     * Called when the test session has ended, either when there are no more flashcards
     * to test or when the user calls quit.
     */
    @Override
    public void clearTestSession() {
        if (testSession == null) { // if there is no test session to clear
            throw new NoOngoingTestException();
        }

        testSession.getDeck().getStatistics().recordHistory(testSession);
        cardiBuddy.getStatistics().recordHistory(testSession);
        testSession = null;
    }

    /**
     * Checks if there is an ongoing {@code TestSession}.
     * @return boolean
     */
    @Override
    public boolean hasOngoingTestSession() {
        return testSession != null;
    }

    /**
     * Retrieves the current question's corresponding {@code AnswerType}.
     */
    @Override
    public AnswerType getCurrentAnswerType() {
        return testSession.getCurrentAnswerType();
    }

    /**
     * Retrieves the current flashcard's corresponding {@code CardType}.
     */
    @Override
    public CardType getCurrentCardType() {
        return testSession.getCurrentCardType();
    }

    /**
     * Retrieves the current flashcard's image path, if any.
     */
    @Override
    public String getCurrentCardPath() {
        return testSession.getCurrentCardPath();
    }



    //=========== Filtered Flashcard List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Deck} backed by the internal list of
     * {@code versionedCardiBuddy}
     */
    @Override
    public ObservableList<Deck> getFilteredDeckList() {
        return filteredDecks;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Flashcard} backed by the internal list of
     * {@code versionedCardiBuddy}
     */
    @Override
    public ObservableList<Card> getFilteredFlashcardList() {
        return filteredFlashcards;
    }

    @Override
    public void updateFilteredDeckList(Predicate<Deck> predicate) {
        requireNonNull(predicate);
        filteredDecks.setPredicate(predicate);
    }

    @Override
    public void updateFilteredFlashcardList(Predicate<Card> predicate) {
        requireNonNull(predicate);
        filteredFlashcards.setPredicate(predicate);
    }

    @Override
    public Statistics getStatistics() {
        return cardiBuddy.getStatistics();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof cardibuddy.model.ModelManager)) {
            return false;
        }

        // state check
        cardibuddy.model.ModelManager other = (cardibuddy.model.ModelManager) obj;
        return versionedCardiBuddy.equals(other.versionedCardiBuddy)
                && cardiBuddy.equals(other.cardiBuddy)
                && userPrefs.equals(other.userPrefs)
                && filteredDecks.equals(other.filteredDecks)
                && filteredFlashcards.equals(other.filteredFlashcards);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndo() {
        return versionedCardiBuddy.canUndo();
    }

    @Override
    public boolean canRedo() {
        return versionedCardiBuddy.canRedo();
    }

    @Override
    public void undo() {
        versionedCardiBuddy.undo();
    }

    @Override
    public void redo() {
        versionedCardiBuddy.redo();
    }

    @Override
    public void commitCardiBuddy() {
        versionedCardiBuddy.commit();
    }
}
