package cardibuddy.model.flashcard;

import java.util.Objects;

import cardibuddy.model.deck.Deck;

/**
 * Represents a Flashcard in CardiBuddy.
 */
public class Flashcard {

    private final Question question;
    private final Answer answer;
    private final Deck deck;

    public Flashcard(Deck deck, Question question, Answer answer) {
        this.deck = deck;
        this.question = question;
        this.answer = answer;
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Flashcard)) {
            return false;
        }

        return ((Flashcard) other).getQuestion().equals(getQuestion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Q:")
                .append(getQuestion())
                .append("\nA:")
                .append(getAnswer());
        //getTags().forEach(builder::append); tags integration
        return builder.toString();
    }
}
