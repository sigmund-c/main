//package cardibuddy.storage;
//
//import cardibuddy.commons.exceptions.IllegalValueException;
//import cardibuddy.model.deck.Deck;
//import cardibuddy.model.deck.Title;
//import cardibuddy.model.flashcard.Flashcard;
//import cardibuddy.model.flashcard.Question;
//import cardibuddy.model.flashcard.ShortAnswer;
//import cardibuddy.model.tag.Tag;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class JsonAdaptedDeck {
//    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Flashcard's %s field is missing!";
//
//    private final String title;
//
//    /**
//     * Constructs a {@code JsonAdaptedFlashcard} with the given flashcard details.
//     */
//    @JsonCreator
//    public JsonAdaptedFlashcard(@JsonProperty("question") String question, @JsonProperty("answer") String answer,
//                                @JsonProperty("tagged") List<cardibuddy.storage.JsonAdaptedTag> tagged) {
//        this.question = question;
//        this.answer = answer;
//        if (tagged != null) {
//            this.tagged.addAll(tagged);
//        }
//    }
//
//    /**
//     * Converts a given {@code Flashcard} into this class for Jackson use.
//     */
//    public JsonAdaptedFlashcard(Flashcard source) {
//        question = source.getQuestion().toString();
//        answer = source.getAnswer().toString();
////        tagged.addAll(source.getDeck().getTags().stream()
////                .map(JsonAdaptedTag::new)
////                .collect(Collectors.toList()));
//    }
//
//    public JsonAdaptedDeck(Deck source) {
//        title = source.getTitle().toString();
//    }
//
//    /**
//     * Converts this Jackson-friendly adapted flashcard object into the model's {@code Flashcard} object.
//     *
//     * @throws IllegalValueException if there were any data constraints violated in the adapted flashcard.
//     */
//    public Flashcard toModelType() throws IllegalValueException {
//        final List<Tag> flashcardTags = new ArrayList<>();
//        for (cardibuddy.storage.JsonAdaptedTag tag : tagged) {
//            flashcardTags.add(tag.toModelType());
//        }
//
//        final Set<Tag> modelTags = new HashSet<>(flashcardTags);
//        Title title = new Title("temp");
//        Deck deck = new Deck(title, modelTags);
//        Question testQuestion = new Question(question);
//        ShortAnswer testAnswer = new ShortAnswer(answer);
//        return new Flashcard(null, testQuestion, testAnswer);
//    }
//}
