package cardibuddy.model.testsession;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.*;
import cardibuddy.model.tag.Tag;
import cardibuddy.model.testsession.exceptions.AlreadyCorrectException;
import cardibuddy.model.testsession.exceptions.EmptyDeckException;
import cardibuddy.model.testsession.exceptions.UnansweredQuestionException;
import cardibuddy.ui.FlashcardCard;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestSessionTest {

//    static final Deck DECK_EMPTY = new DeckStub(0);
//    static final Deck[] DECKS_NON_EMPTY = new Deck[]{new DeckStub(1), new DeckStub(2), new DeckStub(10), new DeckStub(15)};
//    static final Deck[] DECKS_MORE_THAN_ONE_CARD = new Deck[]{new DeckStub(2), new DeckStub(5), new DeckStub(10), new DeckStub(12)};

    ArrayList<Card> getFlashcard(Deck deck) {
        ArrayList<Card> result = new ArrayList<>();
        result.add(new Flashcard(deck, new Question("Hello"), new McqAnswer("A"), ""));
        return result;
    }

    String randomString1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
    String randomString2 = "abcdefghijklmnopqrstuvwxyz";

    Flashcard generateRandomCard() {
        int option = new Random().nextInt(2 + 1);
        int random1 = new Random().nextInt(26);
        int random2 = new Random().nextInt(26);
        int random3 = new Random().nextInt(26);
        QuestionStub question;
        Answer answer;
        String questionString = "HELLO" + randomString1.charAt(random1) + randomString2.charAt(random2) + randomString1.charAt(random3);
        switch (option){
        case 0: // for MCQ questions
            question = new QuestionStub(questionString);
            answer = new McqAnswerStub("A");
            break;
        case 1: // For TF questions
            question = new QuestionStub(questionString);
            answer = new TfAnswerStub("T");
            break;
        default: // for short answer questions
            question = new QuestionStub(questionString);
            answer = new ShortAnswerStub("Short Answer");
            break;
        }
        return new CardStub(deck, question, answer, "");
    }

    TestSession submitCorrectAnswer(TestSession testSession, int index) {
        String correctAnswer = deck.getFlashcardList().get(index).getAnswer().toString();
        testSession.submitAnswer(correctAnswer);
        return testSession;
    }

    TestSession submitWrongAnswer(TestSession testSession) {
        testSession.submitAnswer("blahblahblahblah");
        return testSession;
    }



    private DeckStub deck = new DeckStub(new Title("HELLO"), new HashSet<>());
   // private final DeckStub deck = new DeckStub(flashcards);
   // private final DeckStub deck = new DeckStub(10);
    //private TestSession testSession;

    @BeforeEach
    void setUp() {
        deck = new DeckStub(new Title("HELLO"), new HashSet<>());
        deck.addFlashcard(generateRandomCard());
        deck.addFlashcard(generateRandomCard());
        deck.addFlashcard(generateRandomCard());
    }

    @AfterEach
    void tearDown() {
       deck = null;
    }

    @Description("Test that getFirstQuestion() method works as expected by returning the correct question.")
    @Test
    void testGetFirstQuestion() {
        TestSession testSession = new TestSession(deck);
        assertEquals(deck.getFlashcardList().get(0).getQuestion(), testSession.getFirstQuestion());
    }

    @Description("Ensure that the constructor throws an EmptyDeckException if the deck provided has no flashcards.")
    @Test
    void testConstructorWithEmptyDeck() {
        assertThrows(EmptyDeckException.class, () -> new TestSession(new Deck(new Title("HELLO"), new HashSet<>(), new ArrayList<Card>())));
    }

    @Description("Ensure that the proper test queue is created when a non empty deck is passed to the constructor.")
    @Test
    void testConstructorWithNonEmptyDeck() {
        TestSession testSession = new TestSession(deck);
        LinkedList<Card> createdQueue = testSession.getTestQueue();
        LinkedList<Card> expectedQueue = new LinkedList<>(deck.getFlashcardList());
        assertEquals(expectedQueue,createdQueue);
    }

    @Description("Test the submission of a correct answer by ensuring that the size of the test queue decreases by 1.")
    @Test
    void testSubmitCorrectAnswer() {
        TestSession testSession = new TestSession(deck);
        testSession.getFirstQuestion();
        testSession = submitCorrectAnswer(testSession, 0);
        int expectedTestQueueSize = deck.getFlashcardList().size() - 1;
        assertEquals(expectedTestQueueSize, testSession.getTestQueueSize());
    }

    @Description("Test the submission of a wrong answer by ensuring that the size of the test queue remains the same (as the wrong answer is added back into the test queue).")
    @Test
    void testSubmitWrongAnswer() {
        TestSession testSession = new TestSession(deck);
        testSession.getFirstQuestion();
        testSession = submitWrongAnswer(testSession);
        int expectedTestQueueSize = deck.getFlashcardList().size();
        assertEquals(expectedTestQueueSize, testSession.getTestQueueSize());
    }

    @Description("Test that skipping a question answered correctly leads to an AlreadyCorrectException thrown")
    @Test
    void testSkipCorrectAnswer() {
        TestSession testSession = new TestSession(deck);
        testSession.getFirstQuestion();
        testSession = submitCorrectAnswer(testSession, 0);
        assertThrows(AlreadyCorrectException.class, testSession::skipQuestion);
    }

    @Description("Test that the correct second question is returned.")
    @Test
    void testGetNextQuestion() {
        TestSession testSession = new TestSession(deck);
        testSession.getFirstQuestion();
        testSession = submitWrongAnswer(testSession);
        Question nextQuestion = testSession.getNextQuestion();
        Question expectedQuestion = deck.getFlashcardList().get(1).getQuestion();
        assertEquals(expectedQuestion, nextQuestion);
    }
    @Description("Test that results of the test session are stored in the {@code HashMap}.")
    @Test
    void testAnsweringQuestions() {
        TestSession testSession = new TestSession(deck);
        testSession.getFirstQuestion();
        testSession = submitCorrectAnswer(testSession, 0);
        testSession.getNextQuestion();
        testSession = submitWrongAnswer(testSession);
        int resultsSize = testSession.getTestResults().size();
        assertEquals(2, resultsSize);
    }

    @Description("Test that force correct throws UnansweredQuestionException if the question is not answered.")
    @Test
    void testForceCorrectThrowsUnansweredQuestionException() {
        TestSession testSession = new TestSession(deck);
        testSession.getFirstQuestion();
        assertThrows(UnansweredQuestionException.class, testSession::forceCorrect);
    }

    @Description("Test that force correct leads to the test queue size decreasing by 1")
    @Test
    void testForceCorrectRemovesReaddedFlashcard() {
        TestSession testSession = new TestSession(deck);
        testSession.getFirstQuestion();
        testSession = submitWrongAnswer(testSession);
        testSession.forceCorrect();
        int expectedTestQueueSize = deck.getFlashcardList().size() - 1;
        assertEquals(expectedTestQueueSize, testSession.getTestQueueSize());
    }

    @Description("Test that force correct throws ALreadyCorrectException.")
    @Test
    void testForceCorrectThrowsAlreadyCorrectException() {
        TestSession testSession = new TestSession(deck);
        testSession.getFirstQuestion();
        testSession = submitCorrectAnswer(testSession, 0);
        assertThrows(AlreadyCorrectException.class, testSession::forceCorrect);
    }
//    /**
//     * Test the creation of the test queue in the constructor based on the given non empty deck.
//     */
//    @Test
//    static void testConstructorWithNonEmptyDeck() {
//        Deck[] decks = DECKS_NON_EMPTY;
//        ArrayList<TestSession> testSessions = getNewlyCreatedTestSessions(decks);
//        for (int i = 0; i < decks.length; i++) {
//            Deck deck = decks[i];
//            TestSession testSession = testSessions.get(i);
//            LinkedList<Card> expectedTestQueue = ((DeckStub) deck).getExpectedTestQueue();
//            assertEquals(expectedTestQueue, testSession.getTestQueue());
//        }
//    }
//
//    /**
//     * Ensure that an {@code EmptyDeckException} is thrown when an empty deck is passed to the constructor.
//     */
//    @Test
//    static void testConstructorWithEmptyDeck() {
//        assertThrows(EmptyDeckException.class, () -> new TestSession(DECK_EMPTY));
//    }
//
//    /**
//     * Test that the correct first question is received when the deck given is non empty.
//     */
//    @Test
//    static void testGetFirstQuestionWithNonEmptyDeck() {
//        ArrayList<TestSession> testSessions = getNewlyCreatedTestSessions(DECKS_NON_EMPTY);
//        Card[] firstExpectedCards = getFirstExpectedCards(DECKS_NON_EMPTY);
//        for (int i = 0; i < testSessions.size(); i++) {
//            TestSession testSession = testSessions.get(i);
//            Card firstExpectedCard = firstExpectedCards[i];
//            assertEquals(firstExpectedCard.getQuestion(), testSession.getFirstQuestion());
//        }
//    }
//
//    /**
//     * Ensure that when a correct answer is submitted to the test session, a {@code TestResult} object is created with the correct parameters.
//     * Note that the actual testing of TestResult class will be in a separate test class itself.
//     */
//    @Test
//    static void testSubmitAnswerCreatesCorrectParameters() {
//        ArrayList<TestSession> testSessions = getNewlyCreatedTestSessions(DECKS_NON_EMPTY);
//        Card[] firstExpectedCards = getFirstExpectedCards(DECKS_NON_EMPTY);
//        for (int i = 0; i < testSessions.size(); i++) {
//            TestSession testSession = testSessions.get(i);
//            testSession.getFirstQuestion();
//            Card firstExpectedCard = firstExpectedCards[i];
//            TestResult testResult = testSession.submitAnswer(firstExpectedCard.getAnswer().toString());
//            assertEquals(testResult.getNumTries(), 1);
//            assertEquals(testResult.getFlashcardAnswer(), firstExpectedCard.getAnswer());
//            assertEquals(testResult.getUserAnswer(), firstExpectedCard.getAnswer().toString());
//        }
//    }
//
//    /**
//     * Ensure that an {@code UnansweredQuestionException} is thrown when the user tries to get the next question without answering the current question.
//     */
//    @Test
//    static void testGetNextQuestionThrowsUnansweredQuestionException() {
//        ArrayList<TestSession> testSessions = getNewlyCreatedTestSessions(DECKS_NON_EMPTY);
//        for (TestSession testSession : testSessions) {
//            assertThrows(UnansweredQuestionException.class,  testSession::getNextQuestion);
//        }
//    }
//
//    /**
//     * Ensure that an {@code AlreadyCorrectException} will be thrown if the user tries to skip a question when the user has already answered it correctly.
//     */
//    @Test
//    static void testSkipQuestionThrowsAlreadyCorrectException() {
//        ArrayList<TestSession> newTestSessions = getNewlyCreatedTestSessions(DECKS_NON_EMPTY);
//        ArrayList<TestSession> firstAnsweredCorrectlyTestSessions = submitCorrectAnswerToQuestions(newTestSessions, DECKS_NON_EMPTY, 0);
//        for (TestSession testSession : firstAnsweredCorrectlyTestSessions) { // test test sessions with their first questions answered
//            assertThrows(AlreadyCorrectException.class, testSession::skipQuestion);
//        }
//    }
//
//    /**
//     * Ensure that when an answer is wrongly answered, skip allows the user to skip the question.
//     * Check that: the test queue has its first card removed, and is not added back into the test.
//     */
//    @Test
//    static void testSkipQuestionDecreasesTestQueueSize() {
//        ArrayList<TestSession> newTestSessions = getNewlyCreatedTestSessions(DECKS_MORE_THAN_ONE_CARD);
//        ArrayList<TestSession> firstAnsweredWronglyTestSessions = submitWrongAnswerToQuestions(newTestSessions, DECKS_MORE_THAN_ONE_CARD, 0);
//        for (int i = 0; i < firstAnsweredWronglyTestSessions.size(); i++) {
//            DeckStub deck = (DeckStub) DECKS_MORE_THAN_ONE_CARD[i];
//            TestSession testSession = firstAnsweredWronglyTestSessions.get(i);
//            LinkedList<Card> expectedTestQueue = deck.getExpectedTestQueue();
//            expectedTestQueue.removeFirst();
//            assertEquals(testSession.getTestQueue(), expectedTestQueue);
//        }
//    }
//
//    /**
//     * Ensure that after submitting the answer, the boolean {@code hasAnswered} will be toggled correctly and the user is able to move to the next question.
//     * Ensure that the size of the testQueue is decremented by 1 as the first question was answered correctly.
//     */
//    @Test
//    static void testGetNextQuestionAfterAnsweringCorrectly() {
//        ArrayList<TestSession> newTestSessions = getNewlyCreatedTestSessions(DECKS_MORE_THAN_ONE_CARD);
//        ArrayList<TestSession> firstAnsweredCorrectlyTestSessions = submitCorrectAnswerToQuestions(newTestSessions, DECKS_MORE_THAN_ONE_CARD, 0);
//        for (int i = 0; i < firstAnsweredCorrectlyTestSessions.size(); i++) {
//            DeckStub deck = (DeckStub) DECKS_MORE_THAN_ONE_CARD[i];
//            Card secondExpectedCard = deck.getSecondExpectedCard(); // get the second flashcard in the deck
//            LinkedList<Card> expectedTestQueue = deck.getExpectedTestQueue();
//            expectedTestQueue.removeFirst();
//
//            TestSession testSession = firstAnsweredCorrectlyTestSessions.get(i);
//            Question nextQuestion = testSession.getNextQuestion(); // the next question in the test queue
//            assertEquals(secondExpectedCard.getQuestion(), nextQuestion);
//            assertEquals(expectedTestQueue, testSession.getTestQueue());
//        }
//    }
//
////    @Test
////    static void testGetNextQuestionAfterAnsweringWrongly() {
////
////    }
//
//    @Test
//    static void testForceCorrectThrowsUnansweredQuestionException() {
//        ArrayList<TestSession> testSessions = getNewlyCreatedTestSessions(DECKS_NON_EMPTY);
//        for (TestSession testSession : testSessions) {
//            assertThrows(UnansweredQuestionException.class, testSession::forceCorrect);
//        }
//    }
//
}
