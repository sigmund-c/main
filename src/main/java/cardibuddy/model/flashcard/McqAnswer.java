package cardibuddy.model.flashcard;

import java.util.List;

import static cardibuddy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * McqAnswer class.
 */
public class McqAnswer implements Answer {
    public static final String MESSAGE_CONSTRAINTS = "MCQ answers should be a single letter corresponding to answer.";

    private String correctAnswer; // should be "a" or "b" or "c" or ....

    public McqAnswer(String correctAnswer) {
        requireNonNull(correctAnswer);
        checkArgument(isValid(correctAnswer), MESSAGE_CONSTRAINTS);
        this.correctAnswer = correctAnswer;
    }

    /**
     * Checks if test length is valid.
     * @param test
     * @return true if ...
     */
    public boolean isValid(String test) {
        if (test.length() != 1) {
            return false;
        }
        if (getNumberForChar(test.charAt(0)) == -1) {
            return false;
        }

        return true;
    }

    /**
     * Javadocs to pass checkstyle.
     * @param toCheck
     * @return true if answer is valid.
     */
    public boolean checkAnswer(String toCheck) {
        requireNonNull(toCheck);
        if (!isValid(toCheck)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        return toCheck.equals(correctAnswer);
    }

    /**
     * Shows the list of possible answers
     * @return a string of all possible answers
     */
    @Override
    public String toString() {
        return correctAnswer;
    }

    // returns the corresponding number for alphabetical letters, eg. a -> 1; b -> 2; c -> 3; ...
    private int getNumberForChar(char c) {
        int num = ((int) c) - 64;
        return num > 0 && num < 27 ? num : -1;
    }

    // returns the corresponding alphabetical letter for numbers
    private char getCharForNumber(int i) { // 1 -> a; 2 -> b; 3 -> c, ...
        return i > 0 && i < 27 ? (char) (i + 64) : null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof McqAnswer)) {
            return false;
        }
        McqAnswer o = (McqAnswer) other;

        return correctAnswer.equals(o.correctAnswer);
    }
}

