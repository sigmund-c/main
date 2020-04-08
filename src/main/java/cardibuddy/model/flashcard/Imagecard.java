package cardibuddy.model.flashcard;

import cardibuddy.model.deck.Deck;

/**
 * Card with an image.
 */
public class Imagecard extends Card {

    private Question question;
    private Answer answer;
    private Deck deck;
    private String path;

    public Imagecard(Deck deck, Question question, Answer answer, String path) {
        super(deck, question, answer, path);
        this.deck = deck;
        this.question = question;
        this.answer = answer;
        this.path = path;
    }

    public CardType getCardType() {
        return CardType.IMAGECARD;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public Question getQuestion() {
        return this.question;
    }

    public Answer getAnswer() {
        return this.answer;
    }

    public boolean checkAnswer(String toCheck) {
        return answer.checkAnswer(toCheck);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\nQ: ")
                .append(getQuestion())
                .append("\nA: ")
                .append(getAnswer())
                .append("\nFile Path: ")
                .append(getPath());
        return builder.toString();
    }
}
