package cardibuddy.model.testsession;

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
