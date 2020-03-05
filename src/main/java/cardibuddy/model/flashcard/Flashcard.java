package cardibuddy.model.flashcard;

import java.util.Objects;

/**
 * Represents a Flashcard in CardiBuddy.
 */
public class Flashcard {

    private final Question question;
    private final Answer answer;

    //private final Set<Tag> tags = new HashSet<>(); tags integration

    public Flashcard(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
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
