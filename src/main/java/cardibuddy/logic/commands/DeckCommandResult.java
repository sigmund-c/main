package cardibuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Constructs a {@code CommandResult} with the specified fields.
 */
public class DeckCommandResult extends CommandResult {
    private final boolean isDeck;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public DeckCommandResult(String feedbackToUser, boolean isDeck) {
        super(feedbackToUser);
        requireNonNull(feedbackToUser);
        this.isDeck = isDeck;
    }

    public boolean isDeck() {
        return isDeck;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        DeckCommandResult otherCommandResult = (DeckCommandResult) other;
        return getFeedbackToUser().equals(otherCommandResult.getFeedbackToUser())
                && isDeck == otherCommandResult.isDeck();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFeedbackToUser(), isDeck);
    }
}
