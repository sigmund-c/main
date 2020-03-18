package cardibuddy.model.flashcard;

import static cardibuddy.model.tag.Tag.VALIDATION_REGEX;

/**
 * API of Answer Component.
 */
public interface Answer {

    String MESSAGE_CONSTRAINTS = "A question should have an answer.";

    static boolean isValidAnswer(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    boolean isValid(String test);

    boolean checkAnswer(String toCheck);

    String getCorrectAnswer();
}
