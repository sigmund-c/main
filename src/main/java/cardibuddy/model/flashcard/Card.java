package cardibuddy.model.flashcard;

import cardibuddy.model.deck.Deck;

/**
 * General form of a Card to be inherited into Flashcard and Imagecard.
 */
public abstract class Card {

    private Question question;
    private Answer answer;
    private Deck deck;
    private String path;

    public Card(Deck deck, Question question, Answer answer, String path) {
        this.deck = deck;
        this.question = question;
        this.answer = answer;
        this.path = path;
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

    public String getPath() {
        return this.path;
    }

    public boolean checkAnswer(String toCheck) {
        return answer.checkAnswer(toCheck);
    }

    public boolean isSameFlashcard(Object other) {
        return this.equals(other);
    }

    public abstract CardType getCardType();

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Card)) {
            return false;
        }

        return ((Card) other).getQuestion().equals(getQuestion());
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
