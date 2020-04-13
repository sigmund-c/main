package cardibuddy.model.testsession;

import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
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

    private final Logger logger = LogsCenter.getLogger(TestResult.class.getName());
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
        logger.info("Result computed.");
    }

    /**
     * Constructor for TestResult.
     * Called if the user skips the question.
     * By default, number of tries is set to 1. This can be overriden.
     */
    public TestResult(Result skippedResult) {
        this.result = skippedResult;
        this.numTries = 1;
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
        logger.info("Increasing the recorded number of tries.");
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
