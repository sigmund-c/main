package cardibuddy.model.testsession;

import cardibuddy.model.flashcard.McqAnswer;

public class McqAnswerStub extends McqAnswer {
    String answer;

    public McqAnswerStub(String answerString) {
        super(answerString);
        answer = answerString;
    }

    @Override
    public String toString() {
        return answer;
    }
}
