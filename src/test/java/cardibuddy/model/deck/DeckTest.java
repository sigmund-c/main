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
        Deck deck = new DeckBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> deck.getTags().remove(0));
    }

    @Test
    public void isSameDeck() {
        // same object -> returns true
        assertTrue(ASYNCHRONOUS.isSameDeck(ASYNCHRONOUS));

        // null -> returns false
        assertFalse(ASYNCHRONOUS.isSameDeck(null));

        // different name -> returns false
        Deck editedAsync = new DeckBuilder(ASYNCHRONOUS).withTitle(VALID_TITLE_REACT).build();
        assertFalse(ASYNCHRONOUS.isSameDeck(editedAsync));

        // same name, same phone, different attributes -> returns true
        editedAsync = new DeckBuilder(ASYNCHRONOUS)
                .withTags(VALID_TAG_HARD).build();
        assertTrue(ASYNCHRONOUS.isSameDeck(editedAsync));

        // same name, same email, different attributes -> returns true
        editedAsync = new DeckBuilder(ASYNCHRONOUS)
                .withTags(VALID_TAG_HARD).build();
        assertTrue(ASYNCHRONOUS.isSameDeck(editedAsync));

        // same name, same phone, same email, different attributes -> returns true
        editedAsync = new DeckBuilder(ASYNCHRONOUS).withTags(VALID_TAG_HARD).build();
        assertTrue(ASYNCHRONOUS.isSameDeck(editedAsync));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Deck asyncCopy = new DeckBuilder(ASYNCHRONOUS).build();
        assertTrue(ASYNCHRONOUS.equals(asyncCopy));

        // same object -> returns true
        assertTrue(ASYNCHRONOUS.equals(ASYNCHRONOUS));

        // null -> returns false
        assertFalse(ASYNCHRONOUS.equals(null));

        // different type -> returns false
        assertFalse(ASYNCHRONOUS.equals(5));

        // different deck -> returns false
        assertFalse(ASYNCHRONOUS.equals(REACT));

        // different name -> returns false
        Deck editedAsync = new DeckBuilder(ASYNCHRONOUS).withTitle(VALID_TITLE_REACT).build();
        assertFalse(ASYNCHRONOUS.equals(editedAsync));

        // different tags -> returns false
        editedAsync = new DeckBuilder(ASYNCHRONOUS).withTags(VALID_TAG_HARD).build();
        assertFalse(ASYNCHRONOUS.equals(editedAsync));
    }

    @Test
    public void isSameString() {
        //same Title
        Deck editedAsync = new DeckBuilder(ASYNCHRONOUS).build();
        assertTrue(ASYNCHRONOUS.toString().equals(editedAsync.toString()));
    }


}
