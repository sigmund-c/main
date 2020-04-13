package cardibuddy.model.flashcard;

import static cardibuddy.model.flashcard.ShortAnswer.VALIDATION_REGEX;

import java.util.List;

import cardibuddy.model.flashcard.exceptions.WrongAnswerTypeException;


/**
 * API of Answer Component.
 */
public abstract class Answer {

    static String TF_REGEX = "(?=.{1}$)^[TF]";
    static String MCQ_REGEX = "[A][)].*|[B][)].*|[C][)].*";

    /**
     * Checks if it is a valid short answer and not a true/false answer.
     * @param text answer
     * @return true if it is not a true/false answer
     * @throws WrongAnswerTypeException for wrong answer
     */
    public static boolean isValidShortAnswer(String text) throws WrongAnswerTypeException {
        if (text.toLowerCase().equals("true") || text.toLowerCase().equals("false")) {
            throw new WrongAnswerTypeException(text);
        }
        return text.matches(VALIDATION_REGEX);
    }

    public abstract List getAnswerList();

    /**
     * Checks if it is a valid true/false answer.
     * @param text T or F
     * @return false if the requirements are not fulfilled
     * @throws WrongAnswerTypeException for wrong format
     */
    public static boolean isTrueFalseAnswer(String text) throws WrongAnswerTypeException {
        if (text.equals("t") || text.equals("f")) {
            throw new WrongAnswerTypeException(text);
        }
        return text.matches(TF_REGEX);
    }

    public static boolean isMcqAnswer(String text) {
        return text.matches(MCQ_REGEX);
    }

    public abstract boolean isValid(String test);

    public abstract boolean checkAnswer(String toCheck);
}
