package cardibuddy.model.testsession;

import cardibuddy.model.flashcard.TfAnswer;

/**
 * Stub class for TfAnswer.
 */
public class TfAnswerStub extends TfAnswer {

    private String answer;

    public TfAnswerStub(String answerString) {
        super(answerString);
        answer = answerString;
    }

    @Override
    public String toString() {
        return answer;
    }
}
