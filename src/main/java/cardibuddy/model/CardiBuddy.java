package cardibuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.UniqueDeckList;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.UniqueFlashcardList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameFlashcard comparison)
 */
public class CardiBuddy implements ReadOnlyCardiBuddy {

    private final UniqueDeckList decks;
    private final UniqueFlashcardList flashcards;

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
    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards.setFlashcards(flashcards);
    }

    /**
     * Resets the existing data of this {@code CardiBuddy} with {@code newData}.
     */
    public void resetData(ReadOnlyCardiBuddy newData) {
        requireNonNull(newData);

        setDecks(newData.getDeckList());
        setFlashcards(newData.getFlashcardList());
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
     */
    public void addDeck(Deck d) {
        decks.add(d);
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
     */
    public void removeDeck(Deck key) {
        decks.remove(key);
    }

    /**
     * Opens {@code key} from this {@code CardiBuddy}.
     * {@code key} must exist in the cardi buddy.
     */
    public void openDeck(Deck key) {
        decks.open(key);
    }


    //// flashcard-level operations
    /**
     * Returns true if a deck with the same identity as {@code card} exists in cardibuddy.
     */
    public boolean hasFlashcard(Flashcard card) {
        requireNonNull(card);
        return flashcards.contains(card);
    }

    /**
     * Adds a flashcard to the cardi buddy.
     * The flashcard must not already exist in the cardi buddy.
     */
    public void addFlashcard(Flashcard p) {
        flashcards.add(p);
    }

    /**
     * Replaces the given flashcard {@code target} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the cardi buddy.
     * The flashcard identity of {@code editedFlashcard} must not be the
     * same as another existing flashcard in the cardi buddy.
     */
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireNonNull(editedFlashcard);

        flashcards.setFlashcard(target, editedFlashcard);
    }

    /**
     * Removes {@code key} from this {@code CardiBuddy}.
     * {@code key} must exist in the cardi buddy.
     */
    public void removeFlashcard(Flashcard key) {
        flashcards.remove(key);
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
    public ObservableList<Flashcard> getFlashcardList() {
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
