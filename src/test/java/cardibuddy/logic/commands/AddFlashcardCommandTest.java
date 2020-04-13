package cardibuddy.logic.commands;

import static cardibuddy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static cardibuddy.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import cardibuddy.commons.core.GuiSettings;
import cardibuddy.commons.core.LogsCenter;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.Logic;
import cardibuddy.logic.LogicManager;
import cardibuddy.logic.LogicToUi;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Statistics;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.CardType;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.Model;
import cardibuddy.model.ModelManager;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.ReadOnlyUserPrefs;
import cardibuddy.model.tag.Tag;
import cardibuddy.model.testsession.AnswerType;
import cardibuddy.model.testsession.exceptions.EmptyDeckException;
import cardibuddy.model.testsession.TestResult;
import cardibuddy.storage.CardiBuddyStorage;
import cardibuddy.storage.JsonCardiBuddyStorage;
import cardibuddy.storage.JsonUserPrefsStorage;
import cardibuddy.storage.Storage;
import cardibuddy.storage.StorageManager;
import cardibuddy.storage.UserPrefsStorage;
import cardibuddy.testutil.FlashcardBuilder;
import cardibuddy.ui.UiManager;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class AddFlashcardCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    protected static final ArrayList<Card> flashcardsAdded = new ArrayList<>();

    private CommandHistory commandHistory = new CommandHistory();
    private Path userPrefsFilePath = Paths.get("preferences.json");
    private UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
    private Path cardibuddyFilePath = Paths.get("data" , "cardibuddy.json");
    private CardiBuddyStorage cardiBuddyStorage = new JsonCardiBuddyStorage(cardibuddyFilePath);
    private Storage storage = new StorageManager(cardiBuddyStorage, userPrefsStorage);
    private Model model = new ModelManager();
    private Logic logic = new LogicManager(model, storage);
    private UiManager ui = new UiManager(logic);
    private LogicToUiManager logicToUiManager = new LogicToUiManager(ui);

    @Test
    public void equals() {
        Flashcard f1 = new FlashcardBuilder().withQuestion("This card returns true").build();
        Flashcard f2 = new FlashcardBuilder().withQuestion("This card returns false").build();

        AddFlashcardCommand addFlashcardf1Command = new AddFlashcardCommand(f1, logicToUiManager);
        AddFlashcardCommand addFlashcardf2Command = new AddFlashcardCommand(f2, logicToUiManager);

        // same object -> returns true
        assertTrue(addFlashcardf1Command.equals(addFlashcardf1Command));

        // same values -> returns true
        AddFlashcardCommand addFlashcardf1Commandcopy = new AddFlashcardCommand(f1, logicToUiManager);
        assertTrue(addFlashcardf1Command.equals(addFlashcardf1Commandcopy));

        // different types -> returns false
        assertFalse(addFlashcardf1Command.equals(1));

        // null -> returns false
        assertFalse(addFlashcardf1Command.equals(null));

        // different person -> returns false
        assertFalse(addFlashcardf1Command.equals(addFlashcardf2Command));
    }

    private class DeckStub extends Deck {
        // Identity fields
        private final Title title;

        // Data fields
        private final Set<Tag> tags = new HashSet<>();
        private List<Card> flashcards = new ArrayList<>();
        private FilteredList<Card> filteredFlashcards;
        private final Logger logger = LogsCenter.getLogger(Deck.class.getName());
        private Predicate predicate;

        public DeckStub(Title title, Set<Tag> tags, List<Card> flashcards) {
            requireAllNonNull(title, tags);
            this.title = title;
            this.tags.addAll(tags);
            this.flashcards.addAll(flashcards);
            this.filteredFlashcards = new FilteredList<>(FXCollections.observableList(flashcards));
            logger.info("Created Deck");
        }

        public DeckStub(Title title, Set<Tag> tags) {
            requireAllNonNull(title, tags);
            this.title = title;
            this.tags.addAll(tags);
            logger.info("Created Deck");
        }

        public boolean hasFlashcard(Card card) {
            Question cardQuestion = card.getQuestion();
            for (Card c : flashcards) {
                if (cardQuestion.equals(c.getQuestion())) {
                    System.out.println(c.getQuestion().toString());
                    return true;
                }
            }
            return false;
        }

        public List<Card> addCard(Card card) {
            flashcards.add(card);
            filteredFlashcards = new FilteredList<>(FXCollections.observableList(flashcards));
            return Collections.unmodifiableList(flashcards);
        }

        public void updateFilteredFlashcardList(Predicate<Card> predicate) {
            requireNonNull(predicate);
            this.predicate = predicate;
            filteredFlashcards.setPredicate(predicate);
        }
    }

    private class LogicToUiStub implements LogicToUi {

        @Override
        public void openFlashcardPanel(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNewCommandBox() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFlashcardPanel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeFlashcards() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void openStatisticPanel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void openStatisticPanel(int deckIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void openStatisticPanel(int deckIndex, int sessionIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void openDeckPanel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setOpenedDeck(Deck openedDeck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Deck getDisplayedDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isInDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void showTestQuestion(Question question, AnswerType answerType) {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void showTestStatus(int testQueueSize) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void showTestResult(TestResult testResult) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void showTestEnd() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getCardiBuddyFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCardiBuddyFilePath(Path cardiBuddyFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCardiBuddy(ReadOnlyCardiBuddy newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCardiBuddy getCardiBuddy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDeck(Deck target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeck(Deck target, Deck editedDeck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFlashcard(Card flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFlashcard(Card target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFlashcard(Card flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashcard(Card target, Card editedFlashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getTestQueueSize() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Question testDeck(Deck deck) throws EmptyDeckException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TestResult submitAnswer(String userAnswer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void forceCorrect() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Question getNextQuestion() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Question skipQuestion() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearTestSession() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasOngoingTestSession() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AnswerType getCurrentAnswerType() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CardType getCurrentCardType() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getCurrentCardPath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Deck> getFilteredDeckList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Card> getFilteredFlashcardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDeckList(Predicate<Deck> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFlashcardList(Predicate<Card> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitCardiBuddy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Statistics getStatistics() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A LogicToUi stub that contains a single person.
     */
    private class LogicToUiStubWithFlashCard extends LogicToUiStub {
        private final Flashcard flashcard;
        private final Deck displayedDeck;

        LogicToUiStubWithFlashCard(Flashcard flashcard, Deck deck) {
            requireNonNull(flashcard);
            this.flashcard = flashcard;
            displayedDeck = deck;
        }

        @Override
        public Deck getDisplayedDeck() {
            return displayedDeck;
        }

        @Override
        public void updateFlashcardPanel() {
            ui.getMainWindow().updateCards(displayedDeck);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingFlashcardAdded extends ModelStub {
        @Override
        public void addFlashcard(Card flashcard) {
            requireNonNull(flashcard);
            flashcardsAdded.add(flashcard);
        }

        @Override
        public void commitCardiBuddy() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyCardiBuddy getCardiBuddy() {
            return new CardiBuddy();
        }
    }
}
