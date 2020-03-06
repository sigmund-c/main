package cardibuddy.model.flashcard;

public class Question {

    public String questionString;

    public Question(String question) {
        questionString = question;
    }

    @Override
    public String toString() {
        return questionString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Question
                && questionString.equals(((Question) other).questionString));
    }

    @Override
    public int hashCode() {
        return questionString.hashCode();
    }
}
