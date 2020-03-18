package cardibuddy.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cardibuddy.model.deck.Title;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cardibuddy.commons.exceptions.IllegalValueException;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.tag.Tag;
/**
 * Jackson-friendly version of {@link Deck}
 */
public class JsonAdaptedDeck extends JsonAdaptedView {

    /**
     * Jackson-friendly version of {@link Deck}.
     */

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deck's %s field is missing!";

    private final String title;
    private final List<JsonAdaptedFlashcard> flashcards = new ArrayList<>();
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFlashcard} with the given flashcard details.
     */
    @JsonCreator
    public JsonAdaptedDeck(@JsonProperty("title") String title,
                           @JsonProperty("flashcards") List<JsonAdaptedFlashcard> flashcards,
                           @JsonProperty("tagged") List<cardibuddy.storage.JsonAdaptedTag> tagged) {
        this.title = title;
        this.flashcards.addAll(flashcards);
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
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
    }

    /**
     * Converts this Jackson-friendly adapted flashcard object into the model's {@code Flashcard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted flashcard.
     */
    public Deck toModelType() throws IllegalValueException {
        final List<Tag> deckTags = new ArrayList<>();
        for (cardibuddy.storage.JsonAdaptedTag tag : tagged) {
            deckTags.add(tag.toModelType());
        }

        final List<Flashcard> deckFlashcards = new ArrayList<>();
        for (JsonAdaptedFlashcard flashcard : flashcards) {
            deckFlashcards.add(flashcard.toModelType());
        }

        // TODO: add if conditions here to check formatting
        Title modelTitle = new Title(title);
        final Set<Tag> modelTags = new HashSet<>(deckTags);
        return new Deck(modelTitle, modelTags); // TODO: to replace params with actual values
    }

}
