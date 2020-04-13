package cardibuddy.model.flashcard;

import static cardibuddy.logic.commands.CommandTestUtil.VALID_ANSWER_CODE;
import static cardibuddy.logic.commands.CommandTestUtil.VALID_QUESTION_MODULECODE;
import static cardibuddy.testutil.TypicalFlashcards.HELLOWORLD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cardibuddy.testutil.FlashcardBuilder;


public class FlashcardTest {

    @Test
    public void isSameFlashcard() {
        // same object -> returns true
        assertTrue(HELLOWORLD.isSameFlashcard(HELLOWORLD));

        // null -> returns false
        assertFalse(HELLOWORLD.isSameFlashcard(null));

        // different name -> returns false
        Flashcard editedHelloWorld = new FlashcardBuilder(HELLOWORLD).withQuestion(VALID_QUESTION_MODULECODE).build();
        assertFalse(HELLOWORLD.isSameFlashcard(editedHelloWorld));

        // same name, same phone, different attributes -> returns true
        editedHelloWorld = new FlashcardBuilder(HELLOWORLD)
                .withAnswer(VALID_ANSWER_CODE).build();
        assertTrue(HELLOWORLD.isSameFlashcard(editedHelloWorld));

        // same name, same email, different attributes -> returns true
        editedHelloWorld = new FlashcardBuilder(HELLOWORLD)
                .withAnswer(VALID_ANSWER_CODE).build();
        assertTrue(HELLOWORLD.isSameFlashcard(editedHelloWorld));

        // same name, same phone, same email, different attributes -> returns true
        editedHelloWorld = new FlashcardBuilder(HELLOWORLD).withAnswer(VALID_ANSWER_CODE).build();
        assertTrue(HELLOWORLD.isSameFlashcard(editedHelloWorld));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Flashcard asyncCopy = new FlashcardBuilder(HELLOWORLD).build();
        assertTrue(HELLOWORLD.equals(asyncCopy));

        // same object -> returns true
        assertTrue(HELLOWORLD.equals(HELLOWORLD));

        // null -> returns false
        assertFalse(HELLOWORLD.equals(null));

        // different type -> returns false
        assertFalse(HELLOWORLD.equals(5));

        // different name -> returns false
        Flashcard editedHelloWorld = new FlashcardBuilder(HELLOWORLD).withQuestion(VALID_QUESTION_MODULECODE).build();
        assertFalse(HELLOWORLD.equals(editedHelloWorld));
    }

    @Test
    public void isSameString() {
        //same Question
        Flashcard editedHelloWorld = new FlashcardBuilder(HELLOWORLD).build();
        assertTrue(HELLOWORLD.toString().equals(editedHelloWorld.toString()));
    }
}
