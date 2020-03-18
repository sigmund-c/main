package cardibuddy.model.flashcard;

/**
 * Short Answer class for Flashcard.
 */
public class ShortAnswer implements Answer {

    public static final String MESSAGE_CONSTRAINTS = "Short answers should be strings.";

    private String correctAnswer;

    public ShortAnswer(String correctAnswer) {
        if (!isValid(correctAnswer)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.correctAnswer = correctAnswer;
    }

    /**
     * Checks if given answer is a string and is valid.
     * @param test
     * @return true if it is a string.
     */
    public boolean isValid(String test) {
        return true; // as long as its a string its true
    }

    /**
     * Checks if answer matches stored answer.
     * @param toCheck
     * @return true if the string is equal.
     */
    public boolean checkAnswer(String toCheck) {
        if (!isValid(toCheck)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        return toCheck.equals(correctAnswer);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
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
