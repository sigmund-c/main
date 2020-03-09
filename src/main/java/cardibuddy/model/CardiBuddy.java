package cardibuddy.model;

import static java.util.Objects.requireNonNull;

import cardibuddy.model.flashcard.UniqueFlashcardList;
import java.util.List;
import javafx.collections.ObservableList;
import cardibuddy.model.flashcard.Flashcard;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameFlashcard comparison)
 */
public class CardiBuddy implements ReadOnlyCardiBuddy {

    private final UniqueFlashcardList flashcards;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        flashcards = new UniqueFlashcardList();
    }

    public CardiBuddy() {}

    /**
     * Creates an CardiBuddy using the Flashcards in the {@code toBeCopied}
     */
    public CardiBuddy(ReadOnlyCardiBuddy toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code flashcards}.
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

        setFlashcards(newData.getFlashcardList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasFlashcard(Flashcard person) {
        requireNonNull(person);
        return flashcards.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addFlashcard(Flashcard p) {
        flashcards.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedFlashcard} must not be the same as another existing person in the address book.
     */
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireNonNull(editedFlashcard);

        flashcards.setFlashcard(target, editedFlashcard);
    }

    /**
     * Removes {@code key} from this {@code CardiBuddy}.
     * {@code key} must exist in the address book.
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
