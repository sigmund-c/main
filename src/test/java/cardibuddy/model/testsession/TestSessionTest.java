package cardibuddy.model.testsession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.testsession.exceptions.AlreadyCorrectException;
import cardibuddy.model.testsession.exceptions.EmptyDeckException;
import cardibuddy.model.testsession.exceptions.UnansweredQuestionException;
import jdk.jfr.Description;

public class TestSessionTest {

    private String randomString1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
    private String randomString2 = "abcdefghijklmnopqrstuvwxyz";
    private DeckStub deck = new DeckStub(new Title("HELLO"), new HashSet<>());

    /**
     * A method to generate a flashcard of random type, and with a random question string.
     *
     * @return
     */
    Flashcard generateRandomCard() {
        int option = new Random().nextInt(2 + 1);
        int random1 = new Random().nextInt(26);
        int random2 = new Random().nextInt(26);
        int random3 = new Random().nextInt(26);
        QuestionStub question;
        Answer answer;
        String questionString = "HELLO" + randomString1.charAt(random1)
                + randomString2.charAt(random2)
                + randomString1.charAt(random3);
        switch (option) {
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

    @BeforeEach
    void setUp() {
        deck = new DeckStub(new Title("HELLO"), new HashSet<>());
        deck.addCard(generateRandomCard());
        deck.addCard(generateRandomCard());
        deck.addCard(generateRandomCard());
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
        assertThrows(EmptyDeckException.class, () -> new TestSession(new Deck(
                new Title("HELLO"), new HashSet<>(), new ArrayList<Card>())));
    }

    @Description("Ensure that the proper test queue is created when a non empty deck is passed to the constructor.")
    @Test
    void testConstructorWithNonEmptyDeck() {
        TestSession testSession = new TestSession(deck);
        LinkedList<Card> createdQueue = testSession.getTestQueue();
        LinkedList<Card> expectedQueue = new LinkedList<>(deck.getFlashcardList());
        assertEquals(expectedQueue, createdQueue);
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

    @Description("Test the submission of a wrong answer by ensuring that "
            + "the size of the test queue remains the same (as the wrong answer is added back into the test queue).")
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

    @Description("Ensure that the TestResult for a skipped question has been stored as such.")
    @Test
    void testSkippedQuestionStoredAsSkipped() {
        TestSession testSession = new TestSession(deck);
        testSession.getFirstQuestion();
        Card flashcardToCheck = deck.getFlashcardList().get(0);
        testSession.skipQuestion();
        Result storedResult = testSession.getTestResults().get(flashcardToCheck).getResult();
        assertEquals(Result.SKIPPED, storedResult);
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
    void testForceCorrectRemovesReAddedFlashcard() {
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

    @Description("Test that the correct True_False AnswerType is received.")
    @Test
    void testGetCurrentAnswerTypeTrueFalse() {
        DeckStub deck = new DeckStub(new Title("Hi"), new HashSet<>());
        Card card = new CardStub(deck, new Question("Hello"), new TfAnswerStub("T"), "");
        deck.addCard(card);
        TestSession testSession = new TestSession(deck);
        testSession.getFirstQuestion();
        assertEquals(AnswerType.TRUE_FALSE, testSession.getCurrentAnswerType());
    }

    @Description("Test that the correct MCQ AnswerType is received.")
    @Test
    void testGetCurrentAnswerTypeMcq() {
        DeckStub deck = new DeckStub(new Title("Hi"), new HashSet<>());
        Card card = new CardStub(deck, new Question("Hello"), new McqAnswerStub("B"), "");
        deck.addCard(card);
        TestSession testSession = new TestSession(deck);
        testSession.getFirstQuestion();
        assertEquals(AnswerType.MCQ, testSession.getCurrentAnswerType());
    }

    @Description("Test that the correct SHORT_ANSWER AnswerType is received.")
    @Test
    void testGetCurrentAnswerTypeShortAnswer() {
        DeckStub deck = new DeckStub(new Title("Hi"), new HashSet<>());
        Card card = new CardStub(deck, new Question("Hello"), new ShortAnswerStub("yes no"), "");
        deck.addCard(card);
        TestSession testSession = new TestSession(deck);
        testSession.getFirstQuestion();
        assertEquals(AnswerType.SHORT_ANSWER, testSession.getCurrentAnswerType());
    }
}
