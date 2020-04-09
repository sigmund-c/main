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
    public void contains_nullDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.contains(null));
    }

    @Test
    public void contains_deckNotInList_returnsFalse() {
        assertFalse(uniqueDeckList.contains(DJANGO));
    }

    @Test
    public void contains_deckInList_returnsTrue() {
        uniqueDeckList.add(DJANGO);
        assertTrue(uniqueDeckList.contains(DJANGO));
    }

    @Test
    public void contains_deckWithSameIdentityFieldsInList_returnsTrue() {
        uniqueDeckList.add(DJANGO);
        Deck editedDjango = new DeckBuilder(DJANGO).withTags(VALID_TAG_HARD)
                .build();
        assertTrue(uniqueDeckList.contains(editedDjango));
    }

    @Test
    public void add_nullDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.add(null));
    }

    @Test
    public void add_duplicateDeck_throwsDuplicateDeckException() {
        uniqueDeckList.add(DJANGO);
        assertThrows(DuplicateDeckException.class, () -> uniqueDeckList.add(DJANGO));
    }

    @Test
    public void setDeck_nullTargetDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.setDeck(null, DJANGO));
    }

    @Test
    public void setDeck_nullEditedDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.setDeck(DJANGO, null));
    }

    @Test
    public void setDeck_targetDeckNotInList_throwsDeckNotFoundException() {
        assertThrows(DeckNotFoundException.class, () -> uniqueDeckList.setDeck(DJANGO, DJANGO));
    }

    @Test
    public void setDeck_editedDeckIsSameDeck_success() {
        uniqueDeckList.add(DJANGO);
        uniqueDeckList.setDeck(DJANGO, DJANGO);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(DJANGO);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDeck_editedDeckHasSameIdentity_success() {
        uniqueDeckList.add(DJANGO);
        Deck editedDjango = new DeckBuilder(DJANGO).withTags(VALID_TAG_HARD)
                .build();
        uniqueDeckList.setDeck(DJANGO, editedDjango);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(editedDjango);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDeck_editedDeckHasDifferentIdentity_success() {
        uniqueDeckList.add(DJANGO);
        uniqueDeckList.setDeck(DJANGO, REACT);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(REACT);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDeck_editedDeckHasNonUniqueIdentity_throwsDuplicateDeckException() {
        uniqueDeckList.add(DJANGO);
        uniqueDeckList.add(REACT);
        assertThrows(DuplicateDeckException.class, () -> uniqueDeckList.setDeck(DJANGO, REACT));
    }

    @Test
    public void remove_nullDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.remove(null));
    }

    @Test
    public void remove_deckDoesNotExist_throwsDeckNotFoundException() {
        assertThrows(DeckNotFoundException.class, () -> uniqueDeckList.remove(DJANGO));
    }

    @Test
    public void remove_existingDeck_removesDeck() {
        uniqueDeckList.add(DJANGO);
        uniqueDeckList.remove(DJANGO);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDecks_nullUniqueDeckList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.setDecks((UniqueDeckList) null));
    }

    @Test
    public void setDecks_uniqueDeckList_replacesOwnListWithProvidedUniqueDeckList() {
        uniqueDeckList.add(DJANGO);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(REACT);
        uniqueDeckList.setDecks(expectedUniqueDeckList);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDecks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeckList.setDecks((List<Deck>) null));
    }

    @Test
    public void setDecks_list_replacesOwnListWithProvidedList() {
        uniqueDeckList.add(DJANGO);
        List<Deck> deckList = Collections.singletonList(REACT);
        uniqueDeckList.setDecks(deckList);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(REACT);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDecks_listWithDuplicateDecks_throwsDuplicateDeckException() {
        List<Deck> listWithDuplicateDecks = Arrays.asList(DJANGO, DJANGO);
        assertThrows(DuplicateDeckException.class, () -> uniqueDeckList.setDecks(listWithDuplicateDecks));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueDeckList.asUnmodifiableObservableList().remove(0));
    }
}

