package cardibuddy.model.flashcard;

import static cardibuddy.model.flashcard.ShortAnswer.VALIDATION_REGEX;

import java.util.List;

import cardibuddy.model.flashcard.exceptions.WrongTfAnswerTypeException;


/**
 * API of Answer Component.
 */
public abstract class Answer {

    /**
     * Checks if it is a valid short answer and not a true/false answer.
     * @param text answer
     * @return true if it is not a true/false answer
     * @throws WrongTfAnswerTypeException for wrong answer
     */
    public static boolean isValidShortAnswer(String text) throws WrongTfAnswerTypeException {
        if (text.toLowerCase().equals("true") || text.toLowerCase().equals("false")) {
            throw new WrongTfAnswerTypeException(text);
        }
        return text.matches(VALIDATION_REGEX);
    }

    public abstract List getAnswerList();

    /**
     * Checks if it is a valid true/false answer.
     * @param text T or F
     * @return false if the requirements are not fulfilled
     * @throws WrongTfAnswerTypeException for wrong format
     */
    public static boolean isTrueFalseAnswer(String text) throws WrongTfAnswerTypeException {
        if (text.equals("t") || text.equals("f")) {
            throw new WrongTfAnswerTypeException(text);
        }
        String trueFalseRegex = "(?=.{1}$)^[TF]";
        return text.matches(trueFalseRegex);
    }

    /**
     * Checks if it is a valid mcq answer.
     * @param text
     * @return
     */
    public static boolean isMcqAnswer(String text) {
        String mcqRegex = "[A][)].*|[B][)].*|[C][)].*";
        return text.matches(mcqRegex);
    }

    public abstract String getCorrectAnswer();

    public abstract boolean isValid(String test);

    public abstract boolean checkAnswer(String toCheck);
}
