package cardibuddy.model.flashcard;

/**
 * True False answer class.
 */
public class TfAnswer implements Answer {

    public static final String MESSAGE_CONSTRAINTS = "True / False answers should either be \"T\" or \"F\"";

    private String correctAnswer; // should be "T" or "F"

    public TfAnswer(String correctAnswer) {
        if (!isValid(correctAnswer)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.correctAnswer = correctAnswer;
    }

    public boolean isValid(String test) {
        return test.equals("T") || test.equals("F");
    }

    /**
     * Checks if answer is equals to stored answer.
     * @param toCheck
     * @return true if answer is equals.
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
        return "Answer \"T\" for True or \"F\" for False.";
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
