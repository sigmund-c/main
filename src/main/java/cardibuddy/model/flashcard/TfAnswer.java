package cardibuddy.model.flashcard;

import static java.util.Objects.requireNonNull;

import java.util.List;

/**
 * True False answer class.
 */
public class TfAnswer extends Answer {

    public static final String MESSAGE_CONSTRAINTS = "True / False answers should either be \"T\" or \"F\"";
    private static final String TF_REGEX = "(?=.{1}$)^[TF]";

    private String correctAnswer; // should be "T" or "F"

    public TfAnswer(String correctAnswer) {
        requireNonNull(correctAnswer);
        if (!isValid(correctAnswer)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.correctAnswer = correctAnswer;
    }

    public boolean isValid(String test) {
        return test.matches(TF_REGEX);
    }

    /**
     * Checks if answer is equals to stored answer.
     * @param toCheck
     * @return true if answer is equals.
     */
    public boolean checkAnswer(String toCheck) {
        requireNonNull(toCheck);
        if (!isValid(toCheck)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        return toCheck.equals(correctAnswer);
    }

    @Override
    public List getAnswerList() {
        return null;
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
