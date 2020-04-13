package cardibuddy.model;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Statistics;
import cardibuddy.model.flashcard.Card;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a CardiBuddy object.
 */
public interface ReadOnlyCardiBuddy {

    /**
     * Returns an unmodifiable view of the decks list.
     * This list will not contain any duplicate decks.
     */
    ObservableList<Deck> getDeckList();

    /**
     * Returns an unmodifiable view of the flashcards list.
     * This list will not contain any duplicate flashcards.
     */
    ObservableList<Card> getFlashcardList();

    Statistics getStatistics();

}
