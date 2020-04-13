package cardibuddy.model.flashcard;

import static cardibuddy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import cardibuddy.model.flashcard.exceptions.DuplicateFlashcardException;
import cardibuddy.model.flashcard.exceptions.FlashcardNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * A list of flashcards that enforces uniqueness between its elements and does not allow nulls.
 * A flashcard is considered unique by comparing using {@code Flashcard#isSameFlashcard(Flashcard)}.
 * As such, adding and updating of flashcards uses Flashcard#isSameFlashcard(Flashcard)
 * for equality so as to ensure that the flashcard being added or updated is unique in terms of
 * identity in the UniqueFlashcardList. However, the removal of a flashcard uses Flashcard#equals(Object) so
 * as to ensure that the flashcard with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Card#isSameFlashcard(Object) (Flashcard)
 */
public class UniqueFlashcardList implements Iterable<Card> {

    private final ObservableList<Card> internalList = FXCollections.observableArrayList();
    private final ObservableList<Card> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Card toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFlashcard);
    }

    /**
     * Adds a flashcard to the list.
     * The flashcard must not already exist in the list.
     */
    public void add(Card toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFlashcardException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedFlashcard} must not be the same as another existing person in the list.
     */
    public void setFlashcard(Card target, Card editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);

        int index = internalUnmodifiableList.indexOf(target);
        if (index == -1) {
            throw new FlashcardNotFoundException();
        }

        if (!target.isSameFlashcard(editedFlashcard) && internalList.contains(editedFlashcard)) {
            throw new DuplicateFlashcardException();
        }

        internalList.set(index, editedFlashcard);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Card toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FlashcardNotFoundException();
        }
    }

    public void setFlashcards(UniqueFlashcardList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code flashcards}.
     * {@code flashcards} must not contain duplicate flashcards.
     */
    public void setFlashcards(List<Card> flashcards) {
        requireAllNonNull(flashcards);
        if (!flashcardsAreUnique(flashcards)) {
            throw new DuplicateFlashcardException();
        }

        internalList.setAll(flashcards);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Card> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Card> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueFlashcardList // instanceof handles nulls
                        && internalList.equals(((UniqueFlashcardList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code flashcards} contains only unique flashcards.
     */
    private boolean flashcardsAreUnique(List<Card> flashcards) {
        for (int i = 0; i < flashcards.size() - 1; i++) {
            for (int j = i + 1; j < flashcards.size(); j++) {
                if (flashcards.get(i).isSameFlashcard(flashcards.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
