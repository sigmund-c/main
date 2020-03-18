package cardibuddy.model.deck.exceptions;

/**
 * Signals that the operation will result in the creation of a deck with a question and/or an answer.
 */
public class InvalidDeckException extends RuntimeException {
    public InvalidDeckException(String message) {
        super(message);
    }
}



