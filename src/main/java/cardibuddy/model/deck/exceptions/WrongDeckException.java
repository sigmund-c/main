package cardibuddy.model.deck.exceptions;

/**
 * Signals that the deck the operation is being executed in is not the same as the deck that the user has inputted.
 */
public class WrongDeckException extends RuntimeException {
    public WrongDeckException(String message) {
        super(message);
    }
}
