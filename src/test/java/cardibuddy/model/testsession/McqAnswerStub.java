package cardibuddy.model.testsession;

import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.flashcard.McqAnswer;

/**
 * Stub class for McqAnswer.
 */
public class McqAnswerStub extends McqAnswer {
    private String answer = "A";

    public McqAnswerStub(String line) throws ParseException {
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
