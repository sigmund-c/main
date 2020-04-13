package cardibuddy.storage;

import static cardibuddy.storage.JsonAdaptedFlashcard.MISSING_FIELD_MESSAGE_FORMAT;
import static cardibuddy.testutil.Assert.assertThrows;
import static cardibuddy.testutil.TypicalFlashcards.HELLOWORLD;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cardibuddy.commons.exceptions.IllegalValueException;
import cardibuddy.model.deck.Deck;
import cardibuddy.testutil.DeckBuilder;


public class JsonAdaptedFlashcardTest {
    private static final String VALID_DECK = HELLOWORLD.getDeck().toString();
    private static final String VALID_QUESTION = HELLOWORLD.getQuestion().toString();
    private static final String VALID_ANSWER = HELLOWORLD.getAnswer().toString();
    private static final String VALID_PATH = HELLOWORLD.getPath();

    @Test
    public void toModelType_validFlashcardDetails_returnsFlashcard() throws Exception {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_DECK, VALID_QUESTION, VALID_ANSWER, VALID_PATH);
        // The Deck to be input doesn't have to be the same as the one used for JsonAdaptedFlashcard constructor
        Deck deck = new DeckBuilder().build();
        assertEquals(HELLOWORLD, flashcard.toModelType(deck));
    }

    @Test
    public void toModelType_nullDeck_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(null, VALID_QUESTION, VALID_ANSWER, VALID_PATH);
        Deck deck = new DeckBuilder().build();
        assertThrows(IllegalValueException.class, String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Deck.class.getSimpleName()), () -> flashcard.toModelType(deck));
    }

    @Test
    public void toModelType_nullQuestion_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_DECK, null, VALID_ANSWER, VALID_PATH);
        Deck deck = new DeckBuilder().build();
        assertThrows(IllegalValueException.class, String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Deck.class.getSimpleName()), () -> flashcard.toModelType(deck));
    }

    @Test
    public void toModelType_nullAnswer_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_DECK, VALID_QUESTION, null, VALID_PATH);
        Deck deck = new DeckBuilder().build();
        assertThrows(IllegalValueException.class, String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Deck.class.getSimpleName()), () -> flashcard.toModelType(deck));
    }

}
