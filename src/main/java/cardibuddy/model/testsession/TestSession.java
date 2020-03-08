package cardibuddy.model.testsession;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class TestSession {

    public Deck deck;
    public Flashcard current;
    // public Statistic statistics; // for recording statistics

    // space to set deck settings


    // space to set test statistics
    public int numberRight;

    // store the user's answer history
    public HashMap<Flashcard, TestResult> testResults;

    // test queue
    public LinkedList<Flashcard> testQueue;
    // test response strings
    public String CORRECT_STRING = "Correct! The answer is %s.\nType /n to go to the next question.";
    public String WRONG_STRING = "Wrong! The answer is %s.\nType /n to go to the next question.";
    public String END_STRING = "Test complete!";

    /**
     * Constructor for test session. Initiates the test session.
     * @param deck the deck that is being tested
     */
    public TestSession(Deck deck) {
        // initialise variables
        this.deck = deck;
        testQueue = new LinkedList<>(deck.getFlashcards());
        testResults = new HashMap<>();
        next(); // show the first flashcard in the deck
    }



    /**
     * Moves on to the next flashcard in the queue. Sets the current variable to this next flashcard.
     * @return the next flashcard
     */
    public Flashcard next() {
        current = testQueue.removeFirst();
        return current;
    }

    public TestResult test(Answer userAnswer, TestSetting... forceCorrect) {
        if (forceCorrect[0] == TestSetting.FORCE_CORRECT) {
            return new TestResult(userAnswer, Result.CORRECT);
        }
        else if (isCorrect(userAnswer)) {
            return new TestResult(userAnswer, Result.CORRECT);
        }
        else {
            return new TestResult(userAnswer, Result.WRONG;)
        }
    }

    /**
     * Checks the current flashcard's stored answer against
     * @param userAnswer
     * @return
     */
    public boolean isCorrect(Answer userAnswer) {
        Answer answer = current.getAnswer();
        if (answer.equals(userAnswer)) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof TestSession)
                && this.deck.equals(((TestSession) other).deck);
    }
}
