package cardibuddy.model.testsession;

import cardibuddy.model.flashcard.TfAnswer;

public class TfAnswerStub extends TfAnswer {

    String answer;

    public TfAnswerStub(String answerString) {
        super(answerString);
        answer = answerString;
    }

    @Override
    public String toString() {
        return answer;
    }
}
