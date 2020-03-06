package cardibuddy.model.flashcard;

public class Answer {

    private String answerString;

    public Answer(String question) {
        answerString = question;
    }

    @Override
    public String toString() {
        return answerString;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else return (other instanceof Answer) && answerString.equals(other.toString());
    }

    @Override
    public int hashCode() {
        return answerString.hashCode();
    }
}
