package cardibuddy.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.flashcard.ShortAnswer;
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

    private final String question;
    private final String answer;
    private final String deck;
    private final List<cardibuddy.storage.JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFlashcard} with the given flashcard details.
     */
    @JsonCreator
    public JsonAdaptedFlashcard(@JsonProperty("question") String question, @JsonProperty("answer") String answer,
                                @JsonProperty("deck") String deck,
                                @JsonProperty("tagged") List<cardibuddy.storage.JsonAdaptedTag> tagged) {
        this.question = question;
        this.answer = answer;
        this.deck = deck;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Flashcard} into this class for Jackson use.
     */
    public JsonAdaptedFlashcard(Flashcard source) {
        question = source.getQuestion().toString();
        answer = source.getAnswer().toString();
        deck = source.getDeck().toString();
//        tagged.addAll(source.getDeck().getTags().stream()
//                .map(JsonAdaptedTag::new)
//                .collect(Collectors.toList()));
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
      Deck testDeck = new Deck();
        Question testQuestion = new Question(question);
        ShortAnswer testAnswer = new ShortAnswer(answer);
        return new Flashcard(testDeck, testQuestion, testAnswer);
    }
}
