package cardibuddy.model.testsession;

import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Flashcard;

public class TestResult {

    Answer userAnswer;
    Result result;

    public TestResult(Answer userAnswer, Result result) {
        this.userAnswer = userAnswer;
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public Answer getUserAnswer() {
        return userAnswer;
    }
}
