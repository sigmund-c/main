package cardibuddy.model.util;

import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.flashcard.ShortAnswer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import cardibuddy.model.CardiBuddy;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Deck[] getSampleDecks() {
        // Deck A
        Set<Tag> tagsA = new HashSet<>();
        tagsA.add(new Tag("Compulsory"));
        tagsA.add(new Tag("Hard"));
        Deck deckA = new Deck(new Title("CS2103"), tagsA);
        Flashcard cardA = new Flashcard(deckA,
                new Question("What does UML stand for?"),
                new ShortAnswer("Unified Modelling Language"));
        deckA.addFlashcard(cardA);

        return new Deck[]{ deckA };
    }

    public static ReadOnlyCardiBuddy getSampleCardiBuddy() {
        CardiBuddy sampleCb = new CardiBuddy();
        for (Deck sampleDeck : getSampleDecks()) {
            sampleCb.addDeck(sampleDeck);
        }
        return sampleCb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
