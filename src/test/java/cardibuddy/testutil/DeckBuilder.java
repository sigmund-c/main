package cardibuddy.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.tag.Tag;
import cardibuddy.model.util.SampleDataUtil;

/**
 * A utility class to help with building Deck objects.
 */
public class DeckBuilder {

    public static final String DEFAULT_TITLE = "CS1101S";

    private Title title;
    private List<Card> flashcards;
    private Set<Tag> tags;

    public DeckBuilder() {
        title = new Title(DEFAULT_TITLE);
        flashcards = new ArrayList<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the DeckBuilder with the data of {@code deckToCopy}.
     */
    public DeckBuilder(Deck deckToCopy) {
        title = deckToCopy.getTitle();
        flashcards = deckToCopy.getFlashcards();
        tags = new HashSet<>(deckToCopy.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code Deck} that we are building.
     */
    public DeckBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Deck} that we are building.
     */
    public DeckBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Deck build() {
        return new Deck(title, tags, flashcards);
    }

}
