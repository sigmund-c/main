package cardibuddy.model.testsession;

import cardibuddy.model.flashcard.McqAnswer;

/**
 * Stub class for McqAnswer.
 */
public class McqAnswerStub extends McqAnswer {
    private String answer = "A";

    public McqAnswerStub(String line) {
        super(line);
    }

    @Override
    public boolean checkAnswer(String toCheck) {
        if (!(toCheck.equals("A") || toCheck.equals("B") || toCheck.equals("C"))) {
            throw new IllegalArgumentException();
        }
        return answer.equals(toCheck);
    }

    @Override
    public String getCorrectAnswer() {
        return answer;
    }
}
