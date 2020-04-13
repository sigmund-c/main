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
    public String getCorrectAnswer() {
        return answer;
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        if (!(userAnswer.equals("T") || userAnswer.equals("F"))) {
            throw new IllegalArgumentException();
        } else {
            return answer.equals(userAnswer);
        }
    }

    @Override
    public String toString() {
        return answer;
    }
}
