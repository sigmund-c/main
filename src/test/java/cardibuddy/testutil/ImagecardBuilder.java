package cardibuddy.testutil;

import java.util.HashSet;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Imagecard;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.flashcard.ShortAnswer;

/**
 * A utility class to help with building Flashcard objects.
 */
public class ImagecardBuilder {

    public static final String DEFAULT_QUESTION = "What does UML stand for?";
    public static final String DEFAULT_ANSWER = "Unified Modelling Language";

    private Deck deck = new Deck(new Title("Sample"), new HashSet<>());
    private Question question;
    private Answer answer;
    private String path = "";

    public ImagecardBuilder() {
        question = new Question(DEFAULT_QUESTION);
        answer = new ShortAnswer(DEFAULT_ANSWER);
    }

    /**
     * Initializes the FlashcardBuilder with the data of {@code flashcardToCopy}.
     */
    public ImagecardBuilder(Flashcard flashcardToCopy) {
        question = flashcardToCopy.getQuestion();
        answer = flashcardToCopy.getAnswer();
    }

    /**
     * Sets the {@code Answer} of the {@code Flashcard} that we are building.
     */
    public ImagecardBuilder withAnswer(String answer) {
        this.answer = new ShortAnswer(answer);
        return this;
    }

    /**
     * Sets the {@code Question} of the {@code Flashcard} that we are building.
     */
    public ImagecardBuilder withQuestion(String question) {
        this.question = new Question(question);
        return this;
    }

    public Imagecard build() {
        return new Imagecard(deck, question, answer, path);
    }

}

