package cardibuddy.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_DECK = "A deck should not have a question and/or an answer.";
    public static final String MESSAGE_INVALID_DECK_DISPLAYED_INDEX = "The deck index provided is invalid";
    public static final String MESSAGE_INVALID_FLASHCARD = "A flashcard should have a question and an answer.";
    public static final String MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX = "The card index provided is invalid";
    public static final String MESSAGE_NOTHING_TO_UNDO = "There are no commands to undo.";
    public static final String MESSAGE_NOT_IN_DECK = "This operation can only be done in a deck.";
    public static final String MESSAGE_WRONG_DECK = "This operation is being executed in a deck that differs from the"
            + " deck that the user has inputted.";
    public static final String MESSAGE_DECKS_LISTED_OVERVIEW = "%1$d decks listed!";
    public static final String MESSAGE_FLASHCARDS_LISTED_OVERVIEW = "%1$d cards listed!";
    public static final String MESSAGE_DECK_CANNOT_BE_FLASHCARD = "Operation would result in the object created "
            + "being both a deck and a card!";
}
