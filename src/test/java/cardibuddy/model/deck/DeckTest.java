package cardibuddy.model.deck;

import static cardibuddy.logic.commands.CommandTestUtil.VALID_TAG_HARD;
import static cardibuddy.logic.commands.CommandTestUtil.VALID_TITLE_REACT;
import static cardibuddy.testutil.Assert.assertThrows;
import static cardibuddy.testutil.TypicalDecks.ASYNCHRONOUS;
import static cardibuddy.testutil.TypicalDecks.REACT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cardibuddy.testutil.DeckBuilder;

public class DeckTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Deck person = new DeckBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSameDeck() {
        // same object -> returns true
        assertTrue(ASYNCHRONOUS.isSameDeck(ASYNCHRONOUS));

        // null -> returns false
        assertFalse(ASYNCHRONOUS.isSameDeck(null));

        // different phone and email -> returns false
        Deck editedAlice = new DeckBuilder(ASYNCHRONOUS).build();
        assertFalse(ASYNCHRONOUS.isSameDeck(editedAlice));

        // different name -> returns false
        editedAlice = new DeckBuilder(ASYNCHRONOUS).withName(VALID_TITLE_REACT).build();
        assertFalse(ASYNCHRONOUS.isSameDeck(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new DeckBuilder(ASYNCHRONOUS)
                .withTags(VALID_TAG_HARD).build();
        assertTrue(ASYNCHRONOUS.isSameDeck(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new DeckBuilder(ASYNCHRONOUS)
                .withTags(VALID_TAG_HARD).build();
        assertTrue(ASYNCHRONOUS.isSameDeck(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new DeckBuilder(ASYNCHRONOUS).withTags(VALID_TAG_HARD).build();
        assertTrue(ASYNCHRONOUS.isSameDeck(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Deck aliceCopy = new DeckBuilder(ASYNCHRONOUS).build();
        assertTrue(ASYNCHRONOUS.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ASYNCHRONOUS.equals(ASYNCHRONOUS));

        // null -> returns false
        assertFalse(ASYNCHRONOUS.equals(null));

        // different type -> returns false
        assertFalse(ASYNCHRONOUS.equals(5));

        // different person -> returns false
        assertFalse(ASYNCHRONOUS.equals(REACT));

        // different name -> returns false
        Deck editedAlice = new DeckBuilder(ASYNCHRONOUS).withName(VALID_TITLE_REACT).build();
        assertFalse(ASYNCHRONOUS.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new DeckBuilder(ASYNCHRONOUS).build();
        assertFalse(ASYNCHRONOUS.equals(editedAlice));

        // different email -> returns false
        editedAlice = new DeckBuilder(ASYNCHRONOUS).build();
        assertFalse(ASYNCHRONOUS.equals(editedAlice));

        // different address -> returns false
        editedAlice = new DeckBuilder(ASYNCHRONOUS).build();
        assertFalse(ASYNCHRONOUS.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new DeckBuilder(ASYNCHRONOUS).withTags(VALID_TAG_HARD).build();
        assertFalse(ASYNCHRONOUS.equals(editedAlice));
    }
}
