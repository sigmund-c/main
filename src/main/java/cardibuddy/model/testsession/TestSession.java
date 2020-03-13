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


    // hashmap to store the user's answer history
    public HashMap<Flashcard, TestResult> testResults;

    // test queue
    public LinkedList<Flashcard> testQueue;

    /**
     * Constructor for test session. Initiates the test session.
     * @param deck the deck that is being tested
     */
    public TestSession(Deck deck) {
        // initialise variables
        this.deck = deck;
        testQueue = new LinkedList<>(deck.getFlashcards());
        testResults = new HashMap<>();
        this.nextFlashcard(); // show the first flashcard in the deck
    }

    public boolean isEmpty() {
        return testQueue.isEmpty();
    }

    /**
     * Moves on to the next flashcard in the queue, called when the user inputs the command for 'next'.
     * Sets the @var current variable to this next flashcard.
     * @return the next flashcard
     */
    public Flashcard nextFlashcard() {
        if (testQueue.size() > 0) {
            current = testQueue.removeFirst();
            return current;
        }
        else {
            return null; // TODO: change the way to terminate test session
        }
    }

    /**
     * Takes the user's answer for the current flashcard, and tests it against the current flashcard.
     * @param userAnswer Answer object provided by the user.
     * @return the TestResult of the test
     */
    public TestResult test(Answer userAnswer) {
        TestResult result = new TestResult(current.getAnswer(), userAnswer); // get the result of the test
        testResults.put(current, result); // store the current flashcard and the result in the testResults hashmap
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof TestSession)
                && this.deck.equals(((TestSession) other).deck)
                && this.testResults.equals(((TestSession) other).testResults);
    }
}
