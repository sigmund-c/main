package cardibuddy.model.deck;

/**
 * Deck Title class.
 */
public class Title {

    public static final String MESSAGE_CONSTRAINTS =
            "Titles should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "^[\\w]+([-_\\s]{1}[a-z0-9]+)*$";

    private String titleString;

    public Title(String titleString) {
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
