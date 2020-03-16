package cardibuddy.model.util;

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
    private static Set<Tag> tags = new HashSet<>();

    public static Deck[] getSampleDecks() {
        tags.add(new Tag("Y2S1"));
        return new Deck[] {
            new Deck(new Title("CS2105"), tags)
        };
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
