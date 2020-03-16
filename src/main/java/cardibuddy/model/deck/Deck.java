package cardibuddy.model.deck;

import static cardibuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.tag.Tag;



/**
 * Represents a Deck in the cardibuddy application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deck {

    // Identity fields
    private final Title title;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private Set<Flashcard> flashcards = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Deck(Title title, Set<Tag> tags) {
        requireAllNonNull(title, tags);
        this.title = title;
        this.tags.addAll(tags);
    }

    public Title getTitle() {
        return this.title;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Set<Flashcard> getFlashcards() {
        return this.flashcards;
    }

    /**
     * Adds a Flashcard to a Deck.
     * @param card
     * @return the set of Flashcards from the Deck.
     */
    public Set<Flashcard> addFlashcard(Flashcard card) {
        flashcards.add(card);
        return Collections.unmodifiableSet(flashcards);
    }

    /**
     * Checks if the Title of the Deck exists.
     * @param otherDeck the deck being compared to.
     * @return true if the Deck already exists and false otherwise.
     */
    public boolean isSameDeck(Deck otherDeck) {
        if (otherDeck == this) {
            return true;
        }

        return otherDeck != null
                && otherDeck.getTitle().equals(getTitle());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, flashcards, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }


}
