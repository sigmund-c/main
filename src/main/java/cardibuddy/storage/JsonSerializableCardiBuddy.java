package cardibuddy.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import cardibuddy.commons.exceptions.IllegalValueException;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.flashcard.Flashcard;

/**
 * An Immutable CardiBuddy that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableCardiBuddy {

    public static final String MESSAGE_DUPLICATE_PERSON = "Flashcards list contains duplicate flashcard(s).";

    private final List<JsonAdaptedFlashcard> flashcards = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCardiBuddy} with the given flashcards.
     */
    @JsonCreator
    public JsonSerializableCardiBuddy(@JsonProperty("flashcards") List<JsonAdaptedFlashcard> flashcards) {
        this.flashcards.addAll(flashcards);
    }

    /**
     * Converts a given {@code ReadOnlyCardiBuddy} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCardiBuddy}.
     */
    public JsonSerializableCardiBuddy(ReadOnlyCardiBuddy source) {
        flashcards.addAll(source.getFlashcardList().stream().map(JsonAdaptedFlashcard::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code CardiBuddy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CardiBuddy toModelType() throws IllegalValueException {
        CardiBuddy addressBook = new CardiBuddy();
        for (JsonAdaptedFlashcard jsonAdaptedFlashcard : flashcards) {
            Flashcard flashcard = jsonAdaptedFlashcard.toModelType();
            if (addressBook.hasFlashcard(flashcard)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addFlashcard(flashcard);
        }
        return addressBook;
    }

}
