package cardibuddy.testutil;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.flashcard.ShortAnswer;

/**
 * A utility class to help with building Deck objects.
 */
public class CardBuilder {

    public static final String DEFAULT_QUESTION = "What is the powerhouse of the cell?";
    public static final String DEFAULT_ANSWER = "Mitochondria";
    public static final String DEFAULT_PATH = "";

    private Question question;
    private Answer answer;
    private Deck deck;
    private String path;

    public CardBuilder() {
        question = new Question(DEFAULT_QUESTION);
        answer = new ShortAnswer(DEFAULT_ANSWER);
        deck = new DeckBuilder().build();
        path = DEFAULT_PATH;
    }

    /**
     * Initializes the CardBuilder with the data of {@code cardToCopy}.
     */
    public CardBuilder(Card cardToCopy) {
        question = cardToCopy.getQuestion();
        answer = cardToCopy.getAnswer();
        deck = cardToCopy.getDeck();
        path = cardToCopy.getPath();
    }

    /**
     * Sets the {@code Question} of the {@code Card} that we are building.
     */
    public CardBuilder withQuestion(String question) {
        this.question = new Question(question);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code Card} that we are building.
     */
    public CardBuilder withAnswer(String answer) {
        this.answer = new ShortAnswer(answer);
        return this;
    }

    /**
     * Sets the {@code Deck} of the {@code Card} that we are building.
     */
    public CardBuilder withDeck(Deck deck) {
        this.deck = deck;
        return this;
    }

    /**
     * Sets the {@code Path} of the {@code Card} that we are building.
     */
    public CardBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public Card build() {
        return new Flashcard(deck, question, answer, path);
    }
}
