package cardibuddy.model.testsession;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.testsession.exceptions.AlreadyCorrectException;
import cardibuddy.model.testsession.exceptions.EmptyDeckException;
import cardibuddy.model.testsession.exceptions.UnansweredQuestionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class TestSessionTest {

    static Deck deckTenCards = new DeckStub(0); // TODO: Configure deck
    static Deck deckOneCard = new DeckStub(1);
    static Deck deckEmpty = new DeckStub(0);

    TestSession testSessionEmpty = new TestSession(deckEmpty);
    TestSession testSessionOneCard = new TestSession(deckOneCard);
    TestSession testSessionTenCards = new TestSession(deckTenCards);
    TestSession testSessionHalfComplete = getHalfCompleteTestSession(deckTenCards);
    TestSession testSessionEnded;

    /**
     * Get a half complete test for use in testing how {@code TestSession} handles tests that are halfway through.
     * The half complete test session will be awaiting the user's answer.
     * Hence it is possible for the user to: answer and skip.
     * It is not possible for the user to force correct.
     */
    static TestSession getHalfCompleteTestSession(Deck deck) {
        int deckSize = deck.getFlashcardList().size();
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
     * Gets all the non empty decks.
     */
    static Deck[] getNonEmptyDecks() {
        return new Deck[]{deckTenCards, deckOneCard};
    }

    /**
     * Get a test session with the
     */

    /**
     * Test the creation of the test queue in the constructor based on the given non empty deck.
     */
    @Test
    static void testConstructorWithNonEmptyDeck() {
        Deck[] decks = getNonEmptyDecks();
        for (Deck deck : decks) {
            TestSession testSession = new TestSession(deck);
            LinkedList<Card> expectedTestQueue = ((DeckStub) deck).getExpectedTestQueue();
            assertEquals(expectedTestQueue, testSession.getTestQueue());
        }
    }

    /**
     * Ensure that an {@code EmptyDeckException} is thrown when an empty deck is passed to the constructor.
     */
    @Test
    static void testConstructorWithEmptyDeck() {
        assertThrows(EmptyDeckException.class, () -> new TestSession(deckEmpty));
    }

    /**
     * Test that the correct first question is received when the deck given is non empty.
     */
    @Test
    static void testGetFirstQuestionWithNonEmptyDeck() {
        Deck[] decks = getNonEmptyDecks();
        for (Deck deck : decks) {
            TestSession testSession = new TestSession(deck);
            Card firstExpectedCard = ((DeckStub) deck).getFlashcardArrayList().get(0);
            assertEquals(firstExpectedCard.getQuestion(), testSession.getFirstQuestion());
        }
    }

    /**
     * Ensure that when a correct answer is submitted to the test session, a {@code TestResult} object is created with the correct parameters.
     * Note that the actual testing of TestResult class will be in a separate test class itself.
     */
    @Test
    static void testSubmitAnswerCreatesCorrectParameters() {
        Deck[] decks = getNonEmptyDecks();
        for (Deck deck : decks) {
            TestSession testSession = new TestSession(deck);
            testSession.getFirstQuestion();
            Card firstExpectedCard = ((DeckStub) deck).getFlashcardArrayList().get(0);
            TestResult testResult = testSession.submitAnswer(firstExpectedCard.getAnswer().toString());
            assertEquals(testResult.getNumTries(), 1);
            assertEquals(testResult.getFlashcardAnswer(), firstExpectedCard.getAnswer());
            assertEquals(testResult.getUserAnswer(), firstExpectedCard.getAnswer().toString());
        }
    }

    /**
     * Ensure that an {@code UnansweredQuestionException} is thrown when the user tries to get the next question without answering the current question.
     */
    @Test
    static void testGetNextQuestionThrowsUnansweredQuestionException() {
        Deck[] decks = getNonEmptyDecks();
        for (Deck deck : decks) {
            TestSession testSession = new TestSession(deck);
            assertThrows(UnansweredQuestionException.class,  testSession::getNextQuestion);
        }
    }

    /**
     * Ensure that an {@code AlreadyCorrectException} will be thrown if the user tries to skip a question when the user has already answered it correctly.
     */
    @Test
    static void testSkipQuestionThrowsAlreadyCorrectException() {
        Deck[] decks = getNonEmptyDecks();
        for (Deck deck : decks) {
            TestSession testSession = new TestSession(deck);
            testSession.getFirstQuestion();
            Card firstExpectedCard = ((DeckStub) deck).getFlashcardArrayList().get(0);
            testSession.submitAnswer(firstExpectedCard.getAnswer().toString());
            assertThrows(AlreadyCorrectException.class, testSession::skipQuestion);
        }
    }

    /**
     * Ensure that after submitting the answer, the boolean {@code hasAnswered} will be toggled correctly and the user is able to move to the next question.
     * Ensure that the size of the testQueue is decremented by 1 as the first question was answered correctly.
     * @param testSession
     * @param deck
     */
    @Test
    static void testGetNextQuestion() {

    }

    /**
     * Ensure that after submitting the answer, when the user types next,
     */
    @Test
    static void testGetNextQuestionWithNoCardsLeft() {

    }

}
