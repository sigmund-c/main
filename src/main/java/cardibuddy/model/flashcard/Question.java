package cardibuddy.model.flashcard;

import static cardibuddy.commons.util.AppUtil.checkArgument;
import static cardibuddy.model.tag.Tag.VALIDATION_REGEX;
import static java.util.Objects.requireNonNull;

/**
 * Question class.
 */
public class Question {

    public static final String MESSAGE_CONSTRAINTS = "Question should not be left blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private String questionString;

    public Question(String question) {
        requireNonNull(question);
        checkArgument(isValidQuestion(question), MESSAGE_CONSTRAINTS);
        this.questionString = question;
    }

    public static boolean isValidQuestion(String test) {
        return test.matches(VALIDATION_REGEX);
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
