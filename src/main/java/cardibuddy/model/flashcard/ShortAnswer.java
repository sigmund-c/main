package cardibuddy.model.flashcard;

public class ShortAnswer implements Answer {

    private String correctAnswer;

    public ShortAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean isValid(String test) {
        return true; // as long as its a string its true
    }

    public boolean checkAnswer(String toCheck) {
        return toCheck.equals(correctAnswer);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        return "Answer a short string of any combination of letters, numbers and spaces";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else return (other instanceof Answer) && correctAnswer.equals(other.toString());
    }
}
