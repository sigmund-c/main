package cardibuddy.model.testsession;

import cardibuddy.model.flashcard.ShortAnswer;

/**
 * Stub class for ShortAnswer.
 */
public class ShortAnswerStub extends ShortAnswer {

    private String answer;

    public ShortAnswerStub(String answerString) {
        super(answerString);
        answer = answerString;
    }

    @Override
    public String toString() {
        return answer;
    }
}
