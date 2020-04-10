package cardibuddy.model.flashcard;

import java.util.Objects;

import cardibuddy.model.deck.Deck;

/**
 * Represents a Flashcard in CardiBuddy.
 */
public class Flashcard extends Card {

    private Question question;
    private Answer answer;
    private Deck deck;
    private String path;

    public Flashcard(Deck deck, Question question, Answer answer, String path) {
        super(deck, question, answer, "");
        this.deck = deck;
        this.question = question;
        this.answer = answer;
        this.path = "";
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

    /**
     * Checks if the flashcards are of the same identity.
     * @param otherCard
     * @return true if they are the same and false otherwise.
     */
    public boolean isSameFlashcard(Flashcard otherCard) {
        if (otherCard == this) {
            return true;
        }

        return otherCard != null
                && otherCard.getQuestion().equals(getQuestion());
    }

    public CardType getCardType() {
        return CardType.FLASHCARD;
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Q: ")
                .append(getQuestion())
                .append("\nA: ")
                .append(getAnswer());
        return builder.toString();
    }
}
