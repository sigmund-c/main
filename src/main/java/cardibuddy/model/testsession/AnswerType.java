package cardibuddy.model.testsession;

/**
 * An enums class to represent the different {@code Answer} subclasses, for display to the user during  a test session.
 */
public enum AnswerType {
    TRUE_FALSE("(True-False question, answer with 'T' or 'F')"),
    MCQ("(MCQ question, answer with 'A', 'B', 'C' or 'D')"),
    SHORT_ANSWER("(Short answer question)");

    private String description;

    private AnswerType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
