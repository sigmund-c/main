package cardibuddy.model.flashcard.exceptions;

import cardibuddy.logic.parser.exceptions.ParseException;

/**
 * If mcq format is invalid.
 */
public class WrongMcqAnswerTypeException extends ParseException {
    public WrongMcqAnswerTypeException(String message) {
        super(message);
    }
}
