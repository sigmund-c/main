package cardibuddy.model.deck;

import static cardibuddy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Deck Title class.
 */
public class Title {

    public static final String MESSAGE_CONSTRAINTS =
            "Titles should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private String titleString;

    public Title(String titleString) {
        requireNonNull(titleString);

        checkArgument(isValidTitle(titleString), MESSAGE_CONSTRAINTS);
        this.titleString = titleString;
    }

    @Override
    public String toString() {
        return titleString;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            return (other instanceof Title) && titleString.equals(other.toString());
        }
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
