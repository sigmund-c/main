package cardibuddy.model.flashcard.exceptions;

/**
 * Signals that the operation will result in the creation of a flashcard with missing/wrong fields.
 */
public class InvalidFlashcardException extends RuntimeException {
    public InvalidFlashcardException(String message) {
        super(message);
    }
}
