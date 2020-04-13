package cardibuddy.model.flashcard;

import static cardibuddy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.List;

/**
 * Short Answer class for Flashcard.
 */
public class ShortAnswer extends Answer {

    public static final String MESSAGE_CONSTRAINTS = "Short Answer should not be left blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private String correctAnswer;

    public ShortAnswer(String correctAnswer) {
        requireNonNull(correctAnswer);
        checkArgument(isValid(correctAnswer), MESSAGE_CONSTRAINTS);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public List getAnswerList() {
        return null;
    }

    /**
     * Checks if given answer is a string and is valid.
     * @param test
     * @return true if it is not blank
     */
    public boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX); // as long as its a string its true
    }

    /**
     * Checks if answer matches stored answer.
     * @param toCheck
     * @return true if the string is equal.
     */
    public boolean checkAnswer(String toCheck) {
        requireNonNull(toCheck);
        if (!isValid(toCheck)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        return toCheck.equals(correctAnswer);
    }

    @Override
    public String toString() {
        return correctAnswer;
    }

    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            return (other instanceof Answer) && correctAnswer.equals(other.toString());
        }
    }
}
