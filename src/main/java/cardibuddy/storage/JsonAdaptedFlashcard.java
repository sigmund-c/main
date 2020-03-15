package cardibuddy.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cardibuddy.commons.exceptions.IllegalValueException;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Flashcard}.
 */
class JsonAdaptedFlashcard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Flashcard's %s field is missing!";

    private final String name;
    private final String phone;
    private final List<cardibuddy.storage.JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFlashcard} with the given flashcard details.
     */
    @JsonCreator
    public JsonAdaptedFlashcard(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                                @JsonProperty("email") String email, @JsonProperty("address") String address,
                                @JsonProperty("tagged") List<cardibuddy.storage.JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Flashcard} into this class for Jackson use.
     */
    public JsonAdaptedFlashcard(Flashcard source) {
        name = source.getQuestion().toString();
        phone = source.getAnswer().toString();
        tagged.addAll(source.getDeck().getTags().stream()
                .map(cardibuddy.storage.JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted flashcard object into the model's {@code Flashcard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted flashcard.
     */
    public Flashcard toModelType() throws IllegalValueException {
        final List<Tag> flashcardTags = new ArrayList<>();
        for (cardibuddy.storage.JsonAdaptedTag tag : tagged) {
            flashcardTags.add(tag.toModelType());
        }

        final Set<Tag> modelTags = new HashSet<>(flashcardTags);
        return new Flashcard(null, null, null);
    }

}
