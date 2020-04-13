package cardibuddy.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INCOMPLETE_COMMAND = "Incomplete command! \n";
    public static final String MESSAGE_DECK_OR_FLASHCARD_PREFIX = "Include d/ or c/ prefixes to add a deck or card.";
    public static final String MESSAGE_INVALID_DECK = "A deck should not have a question and/or an answer.";
    public static final String MESSAGE_INVALID_DECK_DISPLAYED_INDEX = "The deck index provided is invalid";
    public static final String MESSAGE_INVALID_FLASHCARD = "A flashcard should have a question and an answer.";
    public static final String MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX = "The card index provided is invalid";
    public static final String MESSAGE_INVALID_TWO_WORD_COMMAND = "Remember to add 'card' or 'deck' "
            + "after your command";
    public static final String MESSAGE_NOT_IN_DECK = "This operation can only be done in a deck.";
    public static final String MESSAGE_WRONG_DECK = "The opened deck is different from the deck that"
            + " you have inputted. Please open the correct deck.";
    public static final String MESSAGE_DECKS_LISTED_OVERVIEW = "%1$d decks listed!";
    public static final String MESSAGE_FLASHCARDS_LISTED_OVERVIEW = "%1$d cards listed!";
    public static final String MESSAGE_DECK_CANNOT_BE_FLASHCARD = "Operation would result in the object created "
            + "being both a deck and a card!";
    public static final String MESSAGE_EMPTY_DECK = "The deck contains no flashcards to test!";
    public static final String MESSAGE_NO_TESTSESSION = "There is no test session ongoing!";
    public static final String MESSAGE_UNANSWERED_QUESTION = "%s: Have you answered this question yet?%s";
    public static final String MESSAGE_USER_ANSWER = "Your input: %s";
    public static final String MESSAGE_FLASHCARD_ANSWER = "Correct answer: %s";
    public static final String MESSAGE_NOTHING_TO_REDO = "There are no exceptions to redo.";
    public static final String MESSAGE_NOTHING_TO_UNDO = "There are no exceptions to undo. ";
    public static final String MESSAGE_TEST_COMPLETE = "Test has ended!\n"
            + "Type 'statistics' to show the statistics for this question, or type 'end' to return to the home page.";
    public static final String MESSAGE_TEST_ONGOING = "You can't use this command right now, a test is still ongoing!"
            + "\nType 'quit' if you wish to quit the test.";
}
