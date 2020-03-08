package cardibuddy.model.testsession;

import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Flashcard;

public class TestResult {

    // test response strings
    public String CORRECT_STRING = "Correct! The answer is %s.\nType /n to go to the next question.";
    public String WRONG_STRING = "Wrong! The answer is %s.\nType /n to go to the next question.";
    public String END_STRING = "Test complete!";

    Answer flashcardAnswer; // TODO: may remove this as the flashcard is already stored as the key in the hashmap, so there is no need to store it again
    Answer userAnswer;
    Result result;

    /**
     * Constructor for TestResult.
     * Automatically computes the result based on the given flashcardAnswer and the given userAnswer.
     * @param flashcardAnswer
     * @param userAnswer
     */
    public TestResult(Answer flashcardAnswer, Answer userAnswer) {
        this.flashcardAnswer = flashcardAnswer;
        this.userAnswer = userAnswer;
        result = this.computeResult();
    }

    public Result computeResult() {
        if (flashcardAnswer.equals(userAnswer)) {
            return Result.CORRECT;
        }
        else {
            return Result.WRONG;
        }
    }

    public Result getResult() {
        return result;
    }

    public Answer getUserAnswer() {
        return userAnswer;
    }
}
