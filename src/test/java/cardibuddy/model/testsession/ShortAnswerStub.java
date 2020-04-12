package cardibuddy.model.testsession;

import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.ShortAnswer;

public class ShortAnswerStub extends ShortAnswer {

    String answer;

    public ShortAnswerStub(String answerString) {
        super(answerString);
        answer = answerString;
    }

    @Override
    public String toString() {
        return answer;
    }
}
