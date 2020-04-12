package cardibuddy.testutil;

import java.util.HashSet;
import java.util.Set;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.*;
import cardibuddy.model.tag.Tag;

public class CardBuilder {
    public static final String DEFAULT_QUESTION = "Recursion  is a method of solving a problem where the solution " +
            "depends on solutions to smaller instances of the same problem";
    public static final String DEFAULT_ANSWER = "true";
    public static final String DEFAULT_PATH = "";

    private Title title = new Title("CS1101S");
    private Set<Tag> tags = new HashSet<>();
    private Deck deck;
    private Question question;
    private Answer answer;
    private String path;

    public CardBuilder() {
        deck = new Deck(title, tags);
        answer = new ShortAnswer(DEFAULT_ANSWER);
        question = new Question(DEFAULT_QUESTION);
        path = DEFAULT_PATH;
    }

    /**
     * Initializes the CardBuilder with the data of {@code flashcardToCopy}.
     */
    public CardBuilder(Flashcard flashcardToCopy) {
        deck = flashcardToCopy.getDeck();
        question = flashcardToCopy.getQuestion();
        answer = flashcardToCopy.getAnswer();
        path = flashcardToCopy.getPath();
    }

    /**
     * Initializes the CardBuilder with the data of {@code flashcardToCopy}.
     */
    public CardBuilder(Imagecard imagecardToCopy) {
        deck = imagecardToCopy.getDeck();
        question = imagecardToCopy.getQuestion();
        answer = imagecardToCopy.getAnswer();
        path = imagecardToCopy.getPath();
    }

    /**
     * Sets the deck of the {@code Flashcard} that we are building.
     */
    public CardBuilder withDeck(String title, Set<Tag> tags) {
        this.deck = new Deck(new Title(title), tags);
        return this;
    }

    /**
     * Sets the {@code Question} of the {@code Flashcard} that we are building.
     */
    public CardBuilder withQuestion(String question) {
        this.question = new Question(question);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code Flashcard} that we are building.
     */
    public CardBuilder withTFAnswer(String answer) {
        this.answer = new TfAnswer(answer);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code Flashcard} that we are building.
     */
    public CardBuilder withMcqAnswer(String answer) {
        this.answer = new McqAnswer(answer);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code Flashcard} that we are building.
     */
    public CardBuilder withShortAnswer(String answer) {
        this.answer = new ShortAnswer(answer);
        return this;
    }

    /**
     * Sets the {@code Path} of the {@code Flashcard} that we are building.
     */
    public CardBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public Flashcard buildFlashcard() {
        return new Flashcard(deck, question, answer, path);
    }

    public Imagecard buildImagecard() {
        return new Imagecard(deck, question, answer, path);
    }
}
