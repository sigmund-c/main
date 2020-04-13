package cardibuddy.model.flashcard.exceptions;

import cardibuddy.logic.parser.exceptions.ParseException;

/**
 * Wrong answer type for true/false questions.
 */
public class WrongTfAnswerTypeException extends ParseException {
    public WrongTfAnswerTypeException(String message) {
        super(message);
    }
}
