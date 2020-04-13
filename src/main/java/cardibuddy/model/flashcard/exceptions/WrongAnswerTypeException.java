package cardibuddy.model.flashcard.exceptions;

import cardibuddy.logic.parser.exceptions.ParseException;

/**
 * Wrong answer type for true/false questions.
 */
public class WrongAnswerTypeException extends ParseException {
    public WrongAnswerTypeException(String message) {
        super(message);
    }
}
