package cardibuddy.model.flashcard;

public class TfAnswer implements Answer {

    private String correctAnswer; // should be "T" or "F"

    public TfAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean isValid(String test) {
        return test.equals("T") || test.equals("F");
    }

    public boolean checkAnswer(String toCheck) {
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
        } else return (other instanceof Answer) && correctAnswer.equals(other.toString());
    }

}
