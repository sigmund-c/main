package cardibuddy.model.flashcard;

public class Answer {

    public String answerString;

    public Answer(String question) {
        answerString = question;
    }

    @Override
    public String toString() {
        return answerString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Question
                && answerString.equals(((Question) other).questionString));
    }

    @Override
    public int hashCode() {
        return answerString.hashCode();
    }
}
