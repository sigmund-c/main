package cardibuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Statistics;
import cardibuddy.model.deck.UniqueDeckList;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.UniqueFlashcardList;
import cardibuddy.model.testsession.TestSession;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the CardiBuddy level
 * Duplicates are not allowed
 */
public class CardiBuddy implements ReadOnlyCardiBuddy {

    private final UniqueDeckList decks;
    private final UniqueFlashcardList flashcards;
    private Statistics statistics;
    //private TestSession testSession;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        decks = new UniqueDeckList();
        flashcards = new UniqueFlashcardList();
        statistics = new Statistics();
    }

    public CardiBuddy() {}

    /**
     * Creates a CardiBuddy using the Decks in the {@code toBeCopied}
     */
    public CardiBuddy(ReadOnlyCardiBuddy toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the decks list with {@code decks}.
     * {@code decks} must not contain duplicate decks.
     */
    public void setDecks(List<Deck> decks) {
        this.decks.setDecks(decks);
    }

    /**
     * Replaces the contents of the flashcard list with {@code flashcards}.
     * {@code flashcards} must not contain duplicate flashcards.
     */
    public void setFlashcards(List<Card> flashcards) {
        this.flashcards.setFlashcards(flashcards);
    }

    /**
     * Resets the existing data of this {@code CardiBuddy} with {@code newData}.
     */

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    /**
     * Sets all current data into another set of data. This includes the Decks, Cards, and Statistics.
     */
    public void resetData(ReadOnlyCardiBuddy newData) {
        requireNonNull(newData);

        setDecks(newData.getDeckList());
        setFlashcards(newData.getFlashcardList());
        setStatistics(newData.getStatistics());
    }

    //// deck-level operations
    /**
     * Returns true if a deck with the same identity as {@code deck} exists in cardibuddy.
     */
    public boolean hasDeck(Deck deck) {
        requireNonNull(deck);
        return decks.contains(deck);
    }

    /**
     * Adds a deck to the cardibuddy.
     * The deck must not already exist in the cardibuddy.
     * Also updates both universal {@code Statistics} and deck {@code Statistics}.
     */
    public void addDeck(Deck d) {
        decks.add(d);

        d.getStatistics().trackDeckAdded();
        statistics.trackDeckAdded();
    }

    /**
     * Starts a test for the given deck
     */
    public void startTest(TestSession ts) {

    }

    /**
     * Replaces the given deck {@code target} in the list with {@code editedDeck}.
     * {@code target} must exist in the cardi buddy.
     * The deck identity of {@code editedFlashcard} must not be the same as another existing deck in the cardi buddy.
     */
    public void setDeck(Deck target, Deck editedDeck) {
        requireNonNull(editedDeck);

        decks.setDeck(target, editedDeck);
    }

    /**
     * Removes {@code key} from this {@code CardiBuddy}.
     * {@code key} must exist in the cardi buddy.
     * Also updates both universal {@code Statistics} and deck {@code Statistics}.
     */
    public void removeDeck(Deck key) {
        decks.remove(key);

        key.getStatistics().trackDeckDeleted();
        statistics.trackDeckDeleted();
    }

    //// flashcard-level operations
    /**
     * Returns true if a deck with the same identity as {@code card} exists in cardibuddy.
     */
    public boolean hasFlashcard(Card card) {
        requireNonNull(card);
        return flashcards.contains(card);
    }

    /**
     * Adds a flashcard to the cardi buddy.
     * The flashcard must not already exist in the cardi buddy.
     * Also updates both universal {@code Statistics} and deck {@code Statistics}.
     */
    public void addFlashcard(Card p) {
        flashcards.add(p);

        p.getDeck().getStatistics().trackCardAdded();
        statistics.trackCardAdded();
    }

    /**
     * Replaces the given flashcard {@code target} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the cardi buddy.
     * The flashcard identity of {@code editedFlashcard} must not be the
     * same as another existing flashcard in the cardi buddy.
     */
    public void setFlashcard(Card target, Card editedFlashcard) {
        requireNonNull(editedFlashcard);

        flashcards.setFlashcard(target, editedFlashcard);
    }

    /**
     * Removes {@code key} from this {@code CardiBuddy}.
     * {@code key} must exist in the cardi buddy.
     * Also updates both universal {@code Statistics} and deck {@code Statistics}.
     */
    public void removeFlashcard(Card key) {
        flashcards.remove(key);

        key.getDeck().getStatistics().trackCardDeleted();
        statistics.trackCardDeleted();
    }

    public Statistics getStatistics() {
        return statistics;
    }

    //// util methods

    @Override
    public String toString() {
        return flashcards.asUnmodifiableObservableList().size() + " flashcards";
        // TODO: refine later
    }

    @Override
    public ObservableList<Deck> getDeckList() {
        return decks.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Card> getFlashcardList() {
        return flashcards.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof cardibuddy.model.CardiBuddy // instanceof handles nulls
                && flashcards.equals(((cardibuddy.model.CardiBuddy) other).flashcards));
    }

    @Override
    public int hashCode() {
        return flashcards.hashCode();
    }
}
