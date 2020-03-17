package cardibuddy.model.deck.exceptions;

/**
 * Signals that the operation will result in the creation of an object that functions as a deck and card
 *     simultanerously.
 */
public class DeckCannotBeCardException extends RuntimeException {
    public DeckCannotBeCardException(String message) {
        super(message);
    }
}
