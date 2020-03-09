package cardibuddy.model;

import cardibuddy.model.flashcard.Flashcard;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyCardiBuddy {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Flashcard> getFlashcardList();

}
