package cardibuddy.testutil;

import static cardibuddy.logic.commands.CommandTestUtil.VALID_TAG_FRONTEND;
import static cardibuddy.logic.commands.CommandTestUtil.VALID_TAG_HARD;
import static cardibuddy.logic.commands.CommandTestUtil.VALID_TITLE_DJANGO;
import static cardibuddy.logic.commands.CommandTestUtil.VALID_TITLE_REACT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cardibuddy.model.CardiBuddy;
import cardibuddy.model.deck.Deck;

/**
 * A utility class containing a list of {@code Deck} objects to be used in tests.
 */
public class TypicalDecks {

    public static final Deck ASYNCHRONOUS = new DeckBuilder().withName("Asynchronous Programming")
            .withTags("Hard").build();
    public static final Deck BENSON = new DeckBuilder().withName("Benson Meier")
            .withTags("owesMoney", "friends").build();
    public static final Deck CARL = new DeckBuilder().withName("Carl Kurz").build();
    public static final Deck DANIEL = new DeckBuilder().withName("Daniel Meier").withTags("friends").build();
    public static final Deck ELLE = new DeckBuilder().withName("Elle Meyer").build();
    public static final Deck FIONA = new DeckBuilder().withName("Fiona Kunz").build();
    public static final Deck GEORGE = new DeckBuilder().withName("George Best").build();

    // Manually added
    public static final Deck HOON = new DeckBuilder().withName("Hoon Meier").build();
    public static final Deck IDA = new DeckBuilder().withName("Ida Mueller").build();

    // Manually added - Deck's details found in {@code CommandTestUtil}
    public static final Deck DJANGO = new DeckBuilder().withName(VALID_TITLE_DJANGO)
            .withTags(VALID_TAG_FRONTEND).build();
    public static final Deck REACT = new DeckBuilder().withName(VALID_TITLE_REACT)
            .withTags(VALID_TAG_HARD, VALID_TAG_FRONTEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalDecks() {} // prevents instantiation

    /**
     * Returns an {@code CardiBuddy} with all the typical decks.
     */
    public static CardiBuddy getTypicalCardiBuddy() {
        CardiBuddy ab = new CardiBuddy();
        for (Deck deck : getTypicalDecks()) {
            ab.addDeck(deck);
        }
        return ab;
    }

    public static List<Deck> getTypicalDecks() {
        return new ArrayList<>(Arrays.asList(ASYNCHRONOUS, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
