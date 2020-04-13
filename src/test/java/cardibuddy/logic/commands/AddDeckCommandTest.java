package cardibuddy.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import cardibuddy.commons.core.GuiSettings;
import cardibuddy.logic.CommandHistory;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.Model;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.ReadOnlyUserPrefs;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Statistics;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.CardType;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.testsession.AnswerType;
import cardibuddy.model.testsession.TestResult;
import cardibuddy.model.testsession.exceptions.EmptyDeckException;
import cardibuddy.testutil.DeckBuilder;

import javafx.collections.ObservableList;

public class AddDeckCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        Deck CS2103 = new DeckBuilder().withTitle("CS2103").build();
        Deck CS2101 = new DeckBuilder().withTitle("CS2101").build();
        AddDeckCommand addDeckCS2103Command = new AddDeckCommand(CS2103);
        AddDeckCommand addDeckCS2101Command = new AddDeckCommand(CS2101);

        // same object -> returns true
        assertTrue(addDeckCS2103Command.equals(addDeckCS2103Command));

        // same values -> returns true
        AddDeckCommand addDeckCS2103Commandcopy = new AddDeckCommand(CS2103);
        assertTrue(addDeckCS2103Command.equals(addDeckCS2103Commandcopy));

        // different types -> returns false
        assertFalse(addDeckCS2103Command.equals(1));

        // null -> returns false
        assertFalse(addDeckCS2103Command.equals(null));

        // different person -> returns false
        assertFalse(addDeckCS2103Command.equals(addDeckCS2101Command));
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
     * A Model stub that contains a single person.
     */
    private class ModelStubWithDeck extends ModelStub {
        private final Deck deck;

        ModelStubWithDeck(Deck deck) {
            requireNonNull(deck);
            this.deck = deck;
        }

        @Override
        public boolean hasDeck(Deck deck) {
            requireNonNull(deck);
            return this.deck.isSameDeck(deck);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingDeckAdded extends ModelStub {
        final ArrayList<Deck> decksAdded = new ArrayList<>();

        @Override
        public boolean hasDeck(Deck deck) {
            requireNonNull(deck);
            return decksAdded.stream().anyMatch(deck::isSameDeck);
        }

        @Override
        public void addDeck(Deck deck) {
            requireNonNull(deck);
            decksAdded.add(deck);
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
