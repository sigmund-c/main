package cardibuddy.model;

import static cardibuddy.logic.commands.CommandTestUtil.VALID_TAG_HARD;
import static cardibuddy.testutil.Assert.assertThrows;
import static cardibuddy.testutil.TypicalDecks.DJANGO;
import static cardibuddy.testutil.TypicalDecks.getTypicalCardiBuddy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Statistics;
import cardibuddy.model.deck.exceptions.DuplicateDeckException;
import cardibuddy.model.flashcard.Card;
import cardibuddy.testutil.DeckBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CardiBuddyTest {

    private final CardiBuddy addressBook = new CardiBuddy();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getDeckList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyCardiBuddy_replacesData() {
        CardiBuddy newData = getTypicalCardiBuddy();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateDecks_throwsDuplicateDeckException() {
        // Two decks with the same identity fields
        Deck editedAlice = new DeckBuilder(DJANGO).withTags(VALID_TAG_HARD)
                .build();
        List<Deck> newDecks = Arrays.asList(DJANGO, editedAlice);
        CardiBuddyStub newData = new CardiBuddyStub(newDecks);

        assertThrows(DuplicateDeckException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasDeck_nullDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasDeck(null));
    }

    @Test
    public void hasDeck_deckNotInCardiBuddy_returnsFalse() {
        assertFalse(addressBook.hasDeck(DJANGO));
    }

    @Test
    public void hasDeck_deckInCardiBuddy_returnsTrue() {
        addressBook.addDeck(DJANGO);
        assertTrue(addressBook.hasDeck(DJANGO));
    }

    @Test
    public void hasDeck_deckWithSameIdentityFieldsInCardiBuddy_returnsTrue() {
        addressBook.addDeck(DJANGO);
        Deck editedAlice = new DeckBuilder(DJANGO).withTags(VALID_TAG_HARD)
                .build();
        assertTrue(addressBook.hasDeck(editedAlice));
    }

    @Test
    public void getDeckList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getDeckList().remove(0));
    }

    /**
     * A stub ReadOnlyCardiBuddy whose decks list can violate interface constraints.
     */
    private static class CardiBuddyStub implements ReadOnlyCardiBuddy {
        private final ObservableList<Deck> decks = FXCollections.observableArrayList();
        private final ObservableList<Card> flashcards = FXCollections.observableArrayList();
        private final Statistics statistics = new Statistics();

        CardiBuddyStub(Collection<Deck> decks) {
            this.decks.setAll(decks);
        }

        @Override
        public ObservableList<Deck> getDeckList() {
            return decks;
        }

        @Override
        public ObservableList<Card> getFlashcardList() {
            return flashcards;
        }

        @Override
        public Statistics getStatistics() {
            return statistics;
        }
    }

}

