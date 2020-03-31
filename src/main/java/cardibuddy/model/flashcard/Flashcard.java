package cardibuddy.model.flashcard;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.tag.Tag;

/**
 * Represents a Flashcard in CardiBuddy.
 */
public class Flashcard {

    private final Question question;
    private final Answer answer;
    private final Deck deck;
    private final Set<Tag> tags = new HashSet<>();

    public Flashcard(Deck deck, Question question, Answer answer, Set<Tag> tags) {
        this.deck = deck;
        this.question = question;
        this.answer = answer;
        this.tags.addAll(tags);
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

    public Set<Tag> getTags() {
        return this.tags;
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
                && otherCard.getAnswer().equals(getAnswer());
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
