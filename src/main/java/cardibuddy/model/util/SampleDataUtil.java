package cardibuddy.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import cardibuddy.model.CardiBuddy;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.flashcard.ShortAnswer;
import cardibuddy.model.flashcard.TfAnswer;
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
                new ShortAnswer("Unified Modelling Language"), "");
        Flashcard cardB = new Flashcard(deckA,
                new Question("As per the KISS principle, should one always prefer the simpler solution over more"
                        + " clever solutions?"),
                new TfAnswer("F"), "");
        Flashcard cardC = new Flashcard(deckA,
                new Question("Do non-functional requirements specify the constraints under which system is developed"
                        + " and operated?"),
                new TfAnswer("T"), "");
        Flashcard cardD = new Flashcard(deckA,
                new Question("Is defensive code desirable at all times?"),
                new TfAnswer("F"), "");
        deckA.addCard(cardA);
        deckA.addCard(cardB);
        deckA.addCard(cardC);
        deckA.addCard(cardD);

        // Deck B
        Set<Tag> tagsB = new HashSet<>();
        tagsB.add(new Tag("Hard"));
        Deck deckB = new Deck(new Title("CS2040S"), tagsB);

        // Deck C
        Set<Tag> tagsC = new HashSet<>();
        tagsC.add(new Tag("Javascript"));
        tagsC.add(new Tag("HTML"));
        tagsC.add(new Tag("CSS"));
        tagsC.add(new Tag("Framework"));
        Deck deckC = new Deck(new Title("Vuejs"), tagsC);

        // Deck D
        Set<Tag> tagsD = new HashSet<>();
        tagsD.add(new Tag("Relational"));
        tagsD.add(new Tag("Database"));
        Deck deckD = new Deck(new Title("PostgreSQL"), tagsD);

        // Deck D
        Set<Tag> tagsE = new HashSet<>();
        tagsE.add(new Tag("Cloud"));
        tagsE.add(new Tag("Simple"));
        Deck deckE = new Deck(new Title("Digital Ocean"), tagsE);

        return new Deck[]{ deckA, deckB, deckC, deckD, deckE };
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
