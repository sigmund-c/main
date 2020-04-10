package cardibuddy.model.testsession;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.testsession.exceptions.EmptyDeckException;
import cardibuddy.model.testsession.exceptions.UnansweredQuestionException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class TestSessionTest {

    Deck deckTenCards = new DeckStub(0); // TODO: Configure deck
    Deck deckOneCard = new DeckStub(1);
    Deck deckEmpty = new DeckStub(0);

    TestSession testSessionEmpty = new TestSession(deckEmpty);
    TestSession testSessionOneCard = new TestSession(deckOneCard);
    TestSession testSessionTenCards = new TestSession(deckTenCards);

    TestSession testSessionFirstQuestionAnsweredCorrectly;
    TestSession testSessionFirstQuestionAnsweredWrongly;
    TestSession testSessionHalfCompleteUnansweredPrevAllCorrect;
    TestSession testSessionHalfCompleteAnsweredCorrectly;
    TestSession testSessionHalfCompleteAnsweredWrongly;

    TestSession testSessionEnded;

    /**
     * Get a half complete test for use in testing how {@code TestSession} handles tests that are halfway through.
     * The half complete test session will be awaiting the user's answer.
     * Hence it is possible for the user to: answer and skip.
     * It is not possible for the user to force correct.
     */
    static TestSession getHalfCompleteTestSession(Deck deck) {
        int deckSize = ((DeckStub) deck).getFlashcardArrayList().size();
        int middle = deckSize/2;
        for (int i = 0; i < middle; i++) {

        }
    }

    /**
     * Get a test session with the first question answered correctly
     */
    static TestSession getFirstQuestionAnsweredCorrectlyTestSession(Deck deck) {

    }

    /**
     * Get a test session with the
     */

    /**
     * Test the creation of the test queue in the constructor based on the given non empty deck.
     */
    @ParameterizedTest
    static <ParameterizedTest> void testConstructorWithNonEmptyDeck(Deck deck) {
        TestSession testSession = new TestSession(deck);
        LinkedList<Card> expectedTestQueue = ((DeckStub) deck).getExpectedTestQueue();
        assertEquals(expectedTestQueue, testSession.getTestQueue());
    }

    /**
     * Ensure that an {@code EmptyDeckException} is thrown when an empty deck is passed to the constructor.
     */
    @Test
    static void testConstructorWithEmptyDeck(Deck deck) {
        TestSession testSession = new TestSession(deck);
        assertThrows(EmptyDeckException.class, () -> new TestSession(deck));
    }

    /**
     * Test that the correct first question is received when the deck given is non empty.
     */
    @Test
    static void testGetFirstQuestionWithNonEmptyDeck(Deck deck) {
        TestSession testSession = new TestSession(deck);
        Card firstExpectedCard = ((DeckStub) deck).getFlashcardArrayList().get(0);
        assertEquals(firstExpectedCard.getQuestion(), testSession.getFirstQuestion());
    }

    /**
     * Ensure that when a correct answer is submitted to the test session, a {@code TestResult} object is created with the correct parameters.
     * Note that the actual testing of TestResult class will be in a separate test class itself.
     */
    @Test
    static void testSubmitAnswerCreatesCorrectParameters(Deck deck) {
        TestSession testSession = new TestSession(deck);
        testSession.getFirstQuestion();
        Card firstExpectedCard = ((DeckStub) deck).getFlashcardArrayList().get(0);
        TestResult testResult = testSession.submitAnswer(firstExpectedCard.getAnswer().toString());
        assertEquals(testResult.getNumTries(), 1);
        assertEquals(testResult.getFlashcardAnswer(), firstExpectedCard.getAnswer());
        assertEquals(testResult.getUserAnswer(), firstExpectedCard.getAnswer().toString());
    }

    /**
     * Ensure that an {@code UnansweredQuestionException} is thrown when the user tries to get the next question without answering the current question.
     * @param testSession
     * @param deck
     */
    @Test
    static void testUnansweredQuestionException(Deck deck) {
        TestSession testSession = new TestSession(deck);
        assertThrows(UnansweredQuestionException.class,  testSession::getNextQuestion);
    }

    /**
     * Ensure that an {@code AlreadyCorrectException} will be thrown if the user tries to skip a question when the user has already answered it correctly.
     */
    @Test
    static void testAlreadyCorrectException(Deck deck) {

    }

    /**
     * Ensure that after submitting the answer, the boolean {@code }hasAnswered} will be toggled correctly and the user is able to move to the next question.
     * Ensure that the size of the testQueue is decremented by 1 as the first question was answered correctly.
     * @param testSession
     * @param deck
     */
    @Test
    static void testGetNextQuestion(TestSession answeredTestSession, Deck deck) {

    }

    @Test
    static void testGetNextQuestion(TestSession testSession, Deck deck) {

    }

    @Test
    static void testAnsweringNoException() {

    }
}
