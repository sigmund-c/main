package cardibuddy.model.flashcard;

/**
 * Question class.
 */
public class Question {

    private String questionString;

    public Question(String question) {
        questionString = question;
    }

    @Override
    public String toString() {
        return questionString;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            return (other instanceof Question) && questionString.equals(other.toString());
        }
    }

    @Override
    public int hashCode() {
        return questionString.hashCode();
    }
}
