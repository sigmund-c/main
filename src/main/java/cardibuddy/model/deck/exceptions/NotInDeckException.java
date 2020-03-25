package cardibuddy.model.deck.exceptions;

/**
 * Signals that the operation can only be done in a deck, and the user is currently not in a deck.
 */
public class NotInDeckException extends RuntimeException {
    public NotInDeckException(String message) {
        super(message);
    }
}
