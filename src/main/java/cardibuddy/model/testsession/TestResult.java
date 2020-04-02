package cardibuddy.model.testsession;

import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.testsession.exceptions.AlreadyCorrectException;

/**
 * Test Result class. Stores the user's answer,
 * number of tries user has made for that particular test and the Result (CORRECT or WRONG).
 */
public class TestResult {

    // test response strings
    public static final String CORRECT_STRING = "Correct! The answer is %s.\nType /n to go to the next question.";
    public static final String WRONG_STRING = "Wrong! The answer is %s.\nType /n to go to the next question.";
    public static final String END_STRING = "Test complete!";

    private Answer flashcardAnswer;
    //TODO: may remove this as the flashcard is already stored as
    //the key in the hashmap, so there is no need to store it again
    private String userAnswer;
    private Result result;

    private int numTries;

    /**
     * Constructor for TestResult.
     * Automatically computes the result based on the given flashcardAnswer and the given userAnswer.
     *
     * @param flashcardAnswer
     * @param userAnswer
     */
    public TestResult(Answer flashcardAnswer, String userAnswer) {
        this.flashcardAnswer = flashcardAnswer;
        this.userAnswer = userAnswer;
        this.numTries = 1; // when first created, numTries = 1 by default
        result = this.computeResult();
    }

    /**
     * Constructor for TestResult.
     * Called if the user skips the question
     * when they haven't answered it before in the Test Session.
     */
    public TestResult(Result skippedResult) {
        this.result = skippedResult;
        this.numTries = 1;
    }

    /**
     * Constructor for TestResult.
     * Called if the user has answered the question previously but now chooses to skip it.
     * Records down the number of tries (including the try that the user skips)
     * the user has made for that question before skipping it.
     */
    public TestResult(TestResult prevTestResult) {
        this.result = Result.SKIPPED;
        this.numTries = prevTestResult.getNumTries();
    }

    /**
     * Result of test session.
     *
     * @return Result object (idk what to write here :( ).
     */
    public Result computeResult() {
        if (flashcardAnswer.toString().equals(userAnswer)) {
            return Result.CORRECT;
        } else {
            return Result.WRONG;
        }
    }

    /**
     * Changes the {@code result} to correct.
     */
    public void forceCorrect() throws AlreadyCorrectException {
        if (result == Result.CORRECT) {
            throw new AlreadyCorrectException();
        }
        result = Result.CORRECT;
    }

    /**
     * A getter method for numTries
     *
     * @return the number of tries for this particular question
     */
    public int getNumTries() {
        return numTries;
    }

    public void setNumTries(int numTries) {
        this.numTries = numTries;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result newResult) {
        this.result = newResult;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public Answer getFlashcardAnswer() {
        return flashcardAnswer;
    }
}
