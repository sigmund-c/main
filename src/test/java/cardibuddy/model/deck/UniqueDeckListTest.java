package cardibuddy.model.deck;

import static cardibuddy.logic.commands.CommandTestUtil.VALID_TAG_HARD;
import static cardibuddy.testutil.Assert.assertThrows;
import static cardibuddy.testutil.TypicalDecks.DJANGO;
import static cardibuddy.testutil.TypicalDecks.REACT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import cardibuddy.model.deck.exceptions.DeckNotFoundException;
import cardibuddy.model.deck.exceptions.DuplicateDeckException;
import cardibuddy.testutil.DeckBuilder;

public class UniqueDeckListTest {

    private final UniqueDeckList uniqueDeckList = new UniqueDeckList();

    @Test
    public void containsNullDeckThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.contains(null));
    }

    @Test
    public void containsDeckNotInListReturnsFalse() {
        assertFalse(uniqueDeckList.contains(DJANGO));
    }

    @Test
    public void contains_deckInList_returnsTrue() {
        uniqueDeckList.add(DJANGO);
        assertTrue(uniqueDeckList.contains(DJANGO));
    }

    @Test
    public void containsDeckWithSameIdentityFieldsInListReturnsTrue() {
        uniqueDeckList.add(DJANGO);
        Deck editedDjango = new DeckBuilder(DJANGO).withTags(VALID_TAG_HARD)
                .build();
        assertTrue(uniqueDeckList.contains(editedDjango));
    }

    @Test
    public void addNullDeckThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.add(null));
    }

    @Test
    public void addDuplicateDeckThrowsDuplicateDeckException() {
        uniqueDeckList.add(DJANGO);
        assertThrows(DuplicateDeckException.class, () -> uniqueDeckList.add(DJANGO));
    }

    @Test
    public void setDeckNullTargetDeckThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.setDeck(null, DJANGO));
    }

    @Test
    public void setDeckNullEditedDeckThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.setDeck(DJANGO, null));
    }

    @Test
    public void setDeckTargetDeckNotInListThrowsDeckNotFoundException() {
        assertThrows(DeckNotFoundException.class, () -> uniqueDeckList.setDeck(DJANGO, DJANGO));
    }

    @Test
    public void setDeckEditedDeckIsSameDeckSuccess() {
        uniqueDeckList.add(DJANGO);
        uniqueDeckList.setDeck(DJANGO, DJANGO);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(DJANGO);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDeckEditedDeckHasSameIdentitySuccess() {
        uniqueDeckList.add(DJANGO);
        Deck editedDjango = new DeckBuilder(DJANGO).withTags(VALID_TAG_HARD)
                .build();
        uniqueDeckList.setDeck(DJANGO, editedDjango);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(editedDjango);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDeckEditedDeckHasDifferentIdentitySuccess() {
        uniqueDeckList.add(DJANGO);
        uniqueDeckList.setDeck(DJANGO, REACT);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(REACT);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDeckEditedDeckHasNonUniqueIdentityThrowsDuplicateDeckException() {
        uniqueDeckList.add(DJANGO);
        uniqueDeckList.add(REACT);
        assertThrows(DuplicateDeckException.class, () -> uniqueDeckList.setDeck(DJANGO, REACT));
    }

    @Test
    public void removeNullDeckThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.remove(null));
    }

    @Test
    public void removDeckDoesNotExistThrowsDeckNotFoundException() {
        assertThrows(DeckNotFoundException.class, () -> uniqueDeckList.remove(DJANGO));
    }

    @Test
    public void removeExistingDeckRemovesDeck() {
        uniqueDeckList.add(DJANGO);
        uniqueDeckList.remove(DJANGO);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDecksNullUniqueDeckListThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.setDecks((UniqueDeckList) null));
    }

    @Test
    public void setDecksUniqueDeckListReplacesOwnListWithProvidedUniqueDeckList() {
        uniqueDeckList.add(DJANGO);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(REACT);
        uniqueDeckList.setDecks(expectedUniqueDeckList);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDecksNullListThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.setDecks((List<Deck>) null));
    }

    @Test
    public void setDecksListReplacesOwnListWithProvidedList() {
        uniqueDeckList.add(DJANGO);
        List<Deck> deckList = Collections.singletonList(REACT);
        uniqueDeckList.setDecks(deckList);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(REACT);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDecksListWithDuplicateDeckThrowsDuplicateDeckException() {
        List<Deck> listWithDuplicateDecks = Arrays.asList(DJANGO, DJANGO);
        assertThrows(DuplicateDeckException.class, () -> uniqueDeckList.setDecks(listWithDuplicateDecks));
    }

    @Test
    public void asUnmodifiableObservableListModifyListThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueDeckList.asUnmodifiableObservableList().remove(0));
    }
}

