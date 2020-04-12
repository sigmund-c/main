package cardibuddy.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import cardibuddy.model.CardiBuddy;
import cardibuddy.model.flashcard.Card;

/**
 * A utility class containing a list of {@code Deck} objects to be used in tests.
 */
public class TypicalCards {
    public static final Card card1 = new CardBuilder().withQuestion("This is question 1").
            withTFAnswer("T").buildFlashcard();
    public static final Card card2 = new CardBuilder().withDeck("CS2101", new HashSet<>()).
            withQuestion("This is question 2.").withShortAnswer("ans1").buildFlashcard();
    public static final Card card3 = new CardBuilder().withQuestion("This is question 3").
            withMcqAnswer("a").buildFlashcard();
    public static final Card card4 = new CardBuilder().withDeck("CS2103", new HashSet<>()).
            withQuestion("This is question 4.").withTFAnswer("F").buildImagecard();
    public static final Card card5 = new CardBuilder().withQuestion("This is question 5").
            withShortAnswer("ans2").buildImagecard();
    public static final Card card6 = new CardBuilder().withDeck("CS1101S", new HashSet<>()).
            withQuestion("This is question 6.").withShortAnswer("ans3").buildImagecard();
    public static final Card card7 = new CardBuilder().withQuestion("This is question 7").
            withMcqAnswer("b").buildImagecard();
    public static final Card card8 = new CardBuilder().withDeck("CS3230", new HashSet<>()).
            withQuestion("This is question 8.").withTFAnswer("F").buildFlashcard();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCards() {} // prevents instantiation

    /**
     * Returns an {@code CardiBuddy} with all the typical decks.
     */
    public static CardiBuddy getTypicalCardiBuddy() {
        CardiBuddy cb = new CardiBuddy();
        for (Card card : getTypicalDecks()) {
            cb.addFlashcard(card);
        }
        return cb;
    }

    public static List<Card> getTypicalDecks() {
        return new ArrayList<>(Arrays.asList(card1, card2, card3, card4, card5, card6, card7, card8));
    }
}

