package cardibuddy.model.flashcard;

import cardibuddy.model.deck.Deck;
import java.util.Objects;

/**
 * Represents a Flashcard in CardiBuddy.
 */
public class Flashcard {

    private final Question question;
    private final Answer answer;
    private final Deck deck;

    //private final Set<Tag> tags = new HashSet<>(); tags integration

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
