package cardibuddy.testutil;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.Flashcard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypicalFlashcards {

    public static final Card POWERHOUSE = new CardBuilder().withQuestion("What is the powerhouse of the cell?")
            .withAnswer("Mitochondria").build();
    public static final Card SKY = new CardBuilder().withQuestion("What color is the sky?").withAnswer("Blue").build();
    public static final Card STATEMENT = new CardBuilder().withQuestion("Is this statement true?")
            .withAnswer("True").build();

    private TypicalFlashcards() {}

    public static List<Card> getTypicalFlashcards() {
        return new ArrayList<Card>(Arrays.asList(POWERHOUSE, SKY, STATEMENT));
    }
}
