package cardibuddy.model.testsession;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.flashcard.Answer;
import jdk.jfr.Description;

public class TestResultTest {

    private Answer flashcardAnswer;
    private String userAnswer;

    @BeforeEach
    void setUp() {
        int random = new Random().nextInt(2 + 1);
        switch (random) {
        case 0:
            try {
                flashcardAnswer = new McqAnswerStub("A)H B)E C)Y");
                userAnswer = "F";
            } catch (ParseException e) {
                flashcardAnswer = new TfAnswerStub("T");
                userAnswer = "T";
            }
            break;
        case 1:
            flashcardAnswer = new TfAnswerStub("F");
            userAnswer = "F";
            break;
        case 2:
            flashcardAnswer = new ShortAnswerStub("abc def");
            userAnswer = "abc def";
            break;
        default:
            flashcardAnswer = new ShortAnswerStub("abc def");
            userAnswer = "abc def";
        }
    }

    @AfterEach
    void tearDown() {
        flashcardAnswer = null;
        userAnswer = null;
    }

    @Description("Test that constructor for a TestResult containing SKIPPED is correct.")
    @Test
    void testConstructorForSkippedResult() {
        TestResult testResult = new TestResult(Result.SKIPPED);
        assertEquals(Result.SKIPPED, testResult.getResult());
    }

    @Description("Test that number of tries is set correctly for one try.")
    @Test
    void testGetNumberOfTriesForOneTry() {
        TestResult testResult = new TestResult(flashcardAnswer, userAnswer);
        assertEquals(1, testResult.getNumTries());
    }

    @Description("Test that number of tries is set correctly for more tries.")
    @Test
    void testGetNumberOfTriesForMultipleTries() {
        TestResult testResult = new TestResult(flashcardAnswer, userAnswer);
        int random = new Random().nextInt(20);
        testResult.setNumTries(random);
        assertEquals(random, testResult.getNumTries());
    }

}
