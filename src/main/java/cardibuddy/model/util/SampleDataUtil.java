package cardibuddy.model.util;

import cardibuddy.model.CardiBuddy;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.ShortAnswer;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    static Set<Tag> tags = null;
    static Deck sampleDeck = new Deck(new Title("Sample"),tags);
    public static Flashcard[] getSampleFlashcards() {
        return new Flashcard[] {
            new Flashcard(sampleDeck, new Question("Alex Yeoh"), new ShortAnswer("87438807"))
        };
    }

    public static ReadOnlyCardiBuddy getSampleCardiBuddy() {
        CardiBuddy sampleCb = new CardiBuddy();
        for (Flashcard sampleFlashcard : getSampleFlashcards()) {
            sampleCb.addFlashcard(sampleFlashcard);
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
