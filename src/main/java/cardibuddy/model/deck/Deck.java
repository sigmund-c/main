package cardibuddy.model.deck;

import static cardibuddy.commons.util.CollectionUtil.requireAllNonNull;

import cardibuddy.model.tag.Tag;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Deck in the cardibuddy application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deck {

    // Identity fields
    private final Title title;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Deck(Title title, Set<Tag> tags) {
        requireAllNonNull(title, tags);
        this.title = title;
        this.tags.addAll(tags);
    }

    public Title getTitle() { return title; }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

}
