package cardibuddy.model.testsession;

import java.util.HashMap;
import java.util.LinkedList;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;

/**
 * Test Session class.
 * The test session works as follows:
 * 1. User initiates a test session by the command "test [DECK INDEX]"<br>
 * 2. TestParser parsers this command, and
 * TestSession object testSession is created with the given deck<br>
 * (in future implementations, user can also set test settings,
 * which can mean this class will be a parent class for different kinds of tests)<br>
 * 3. testSession calls nextFlashcard(), which returns the next flashcard in the queue.<br>
 * 4. User inputs their answer to the question, and the parser parses it into an Answer object.
 * testSession calls test() and creates a new TestResult object that stores the user's Answer
 * and the calculated Result by comparing the user's answer with the Flashcard's.<br>
 * 5. testSession returns a {@code TestResult} object.<br>
 * 6. User presses next.
 * (Note in future implementations, user can use force correct
 * to first mark the flashcard as correct, before going to the next question)<br>
 * 7. testSession calls nextFlashcard(), and displays the next flashcard to the user.
 */
public class TestSession {

    private Deck deck;
    private Flashcard current;
    private boolean isOngoing;
    // public Statistic statistics; // for recording statistics

    // space to set deck settings


    // hashmap to store the user's answer history
    private HashMap<Flashcard, TestResult> testResults;

    // test queue
    private LinkedList<Flashcard> testQueue;

    /**
     * Constructor for test session. Initiates the test session.
     *
     * @param deck the deck that is being tested
     */
    public TestSession(Deck deck) {
        // initialise variables
        this.deck = deck;
        testQueue = new LinkedList<>(deck.getFlashcards());
        testResults = new HashMap<>();
    }

    public boolean isEmpty() {
        return testQueue.isEmpty();
    }

    /**
     * Moves on to the next flashcard in the queue, called when the user inputs the command for 'next'.<br>
     * Sets the {@code current} flashcard to this next flashcard.<br>
     * Checks to see if the card should be added to the back of the queue again. (Prioritising)
     * @return the {@code Question} for the next flashcard.
     */
    public Question getNextQuestion() {
        assert !testQueue.isEmpty();
        if (testResults.containsKey(current)) { // if already tested before
            if (testResults.get(current).getResult() == Result.WRONG) { // if user got this flashcard wrong
                testQueue.addLast(current);
            }
        }
        current = testQueue.removeFirst();
        return current.getQuestion();
    }

    /**
     * Shows the answer for the {@code current} flashcard.
     */
    public Answer getAnswer() {
        assert !testQueue.isEmpty() && current != null;
        return current.getAnswer();
    }

    public Flashcard getCurrentFlashcard() {
        return current;
    }

    public LinkedList<Flashcard> getTestQueue() {
        return testQueue;
    }

    /**
     * A method that returns a boolean indicating if the TestSession is complete.
     * A TestSession is complete when there are no more flashcards left in the {@code testQueue}
     */
    public boolean isComplete() {
        return testQueue.isEmpty();
    }



    /**
     * Takes the user's answer for the current flashcard, and tests it against the current flashcard.
     *
     * @param userAnswer Answer object provided by the user.
     * @return the TestResult of the test
     */
    public TestResult test(Answer userAnswer) {
        TestResult result = new TestResult(current.getAnswer(), userAnswer); // get the result of the test
        if (testResults.containsKey(current)) { // if already tested before, update numTries
            TestResult prevResult = testResults.get(current);
            int numTries = prevResult.getNumTries();
            result.setNumTries(numTries + 1); // increase numTries by 1
        }
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
