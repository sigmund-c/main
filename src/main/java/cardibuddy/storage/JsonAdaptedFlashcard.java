package cardibuddy.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cardibuddy.commons.exceptions.IllegalValueException;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Imagecard;
import cardibuddy.model.flashcard.McqAnswer;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.flashcard.ShortAnswer;
import cardibuddy.model.flashcard.TfAnswer;

/**
 * Jackson-friendly version of {@link Flashcard}.
 */
class JsonAdaptedFlashcard extends JsonAdaptedView {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Flashcard's %s field is missing!";

    private final String deck;
    private final String question;
    private final String answer;
    private final String path;

    /**
     * Constructs a {@code JsonAdaptedFlashcard} with the given flashcard details.
     */
    @JsonCreator
    public JsonAdaptedFlashcard(@JsonProperty("deck") String deck, @JsonProperty("question") String question,
                                @JsonProperty("answer") String answer, @JsonProperty("path") String path) {
        this.deck = deck;
        this.question = question;
        this.answer = answer;
        this.path = path;
    }

    /**
     * Converts a given {@code Flashcard} into this class for Jackson use.
     */
    public JsonAdaptedFlashcard(Card source) {
        deck = source.getDeck().toString();
        question = source.getQuestion().toString();
        answer = source.getAnswer().toString();
        path = source.getPath();
    }
    //

    /**
     * Converts this Jackson-friendly adapted flashcard object into the model's {@code Flashcard} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted flashcard.
     */
    public Card toModelType(Deck modelDeck) throws IllegalValueException {
        if (deck == null || question == null || answer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deck.class.getSimpleName()));
        }
        Question modelQuestion = new Question(question);
        Answer modelAnswer;
        if (Answer.isTrueFalseAnswer(answer)) {
            modelAnswer = new TfAnswer(answer);
        } else if (Answer.isMcqAnswer(answer)) {
            modelAnswer = new McqAnswer(answer);
        } else {
            modelAnswer = new ShortAnswer(answer);
        }
        if (path.equals("")) {
            return new Flashcard(modelDeck, modelQuestion, modelAnswer, path);
        } else {
            return new Imagecard(modelDeck, modelQuestion, modelAnswer, path);
        }
    }
}
