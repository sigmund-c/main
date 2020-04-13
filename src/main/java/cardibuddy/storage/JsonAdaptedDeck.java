package cardibuddy.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cardibuddy.commons.exceptions.IllegalValueException;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.exceptions.DuplicateFlashcardException;
import cardibuddy.model.tag.Tag;
/**
 * Jackson-friendly version of {@link Deck}
 */
public class JsonAdaptedDeck extends JsonAdaptedView {

    /**
     * Jackson-friendly version of {@link Deck}.
     */

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deck's %s field is missing!";
    public static final String MESSAGE_DUPLICATE_FLASHCARDS = "Flashcards list contains duplicate flashcard(s).";

    private final String title;
    private final List<JsonAdaptedFlashcard> flashcards = new ArrayList<>();
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final JsonAdaptedStatistic statistics;

    /**
     * Constructs a {@code JsonAdaptedFlashcard} with the given flashcard details.
     */
    @JsonCreator
    public JsonAdaptedDeck(@JsonProperty("title") String title,
                           @JsonProperty("flashcards") List<JsonAdaptedFlashcard> flashcards,
                           @JsonProperty("tagged") List<cardibuddy.storage.JsonAdaptedTag> tagged,
                           @JsonProperty("statistics") JsonAdaptedStatistic statistics) {
        this.title = title;
        if (flashcards != null) {
            this.flashcards.addAll(flashcards);
        }
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.statistics = statistics;
    }

    /**
     * Converts a given {@code Deck} into this class for Jackson use.
     */
    public JsonAdaptedDeck(Deck source) {
        this.title = source.getTitle().toString();
        flashcards.addAll(source.getFlashcards().stream()
                .map(cardibuddy.storage.JsonAdaptedFlashcard::new)
                .collect(Collectors.toList()));
        tagged.addAll(source.getTags().stream()
                .map(cardibuddy.storage.JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        this.statistics = new JsonAdaptedStatistic(source.getStatistics());
    }

    /**
     * Converts this Jackson-friendly adapted flashcard object into the model's {@code Deck} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted deck.
     */
    public Deck toModelType() throws IllegalValueException {
        final List<Tag> deckTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            deckTags.add(tag.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }

        Title modelTitle = new Title(title);
        final Set<Tag> modelTags = new HashSet<>(deckTags);
        Deck newDeck = new Deck(modelTitle, modelTags);

        for (JsonAdaptedFlashcard flashcard : flashcards) {
            try {
                newDeck.addCard(flashcard.toModelType(newDeck));
            } catch (DuplicateFlashcardException e) {
                continue;
            }
        }
        newDeck.setStatistics(statistics.toModeltype());

        // TODO: add if conditions here to check formatting

        return newDeck; // TODO: to replace params with actual values
    }

}
