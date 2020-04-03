package cardibuddy.model.testsession;

/**
 * Enumeration for Results for testing feature.
 */
public enum Result {
    CORRECT("Correct!"),
    WRONG("You got it wrong!"),
    SKIPPED("You skipped this question.");
    private String resultString;

    Result(String resultString) {
        this.resultString = resultString;
    }

    @Override
    public String toString() {
        return resultString;
    }
}
