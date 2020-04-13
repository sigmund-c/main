package cardibuddy.logic.commands;

import static cardibuddy.logic.parser.CliSyntax.PREFIX_ANSWER;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_DECK;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_QUESTION;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.Model;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.SearchDeckKeywordsPredicate;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.SearchCardKeywordsPredicate;
import cardibuddy.testutil.EditCardDescriptorBuilder;
import cardibuddy.testutil.EditDeckDescriptorBuilder;

/**
 * Contains helper methods for testing exceptions.
 */
public class CommandTestUtil {

    public static final String VALID_TITLE_DJANGO = "Django";
    public static final String VALID_TITLE_REACT = "React";
    public static final String VALID_TAG_HARD = "Hard";
    public static final String VALID_TAG_FRONTEND = "Frontend";

    public static final String TITLE_DESC_DJANGO = " " + PREFIX_DECK + VALID_TITLE_DJANGO;
    public static final String TITLE_DESC_REACT = " " + PREFIX_DECK + VALID_TITLE_REACT;
    public static final String TAG_DESC_FRONTEND = " " + PREFIX_TAG + VALID_TAG_FRONTEND;
    public static final String TAG_DESC_HARD = " " + PREFIX_TAG + VALID_TAG_HARD;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_DECK + "PHP&"; // '&' not allowed in names
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "Scripting*"; // '*' not allowed in tags

    public static final String VALID_QUESTION_MODULECODE = "What is the module code of this module?";
    public static final String VALID_ANSWER_CODE = "CS2103";
    public static final String VALID_QUESTION_MODULECODE2 = "What is the module code of this module's twin?";
    public static final String VALID_ANSWER_CODE2 = "CS2101";

    public static final String QUESTION_DESC_MODULECODE = " " + PREFIX_QUESTION + VALID_QUESTION_MODULECODE;
    public static final String ANSWER_DESC_CODE = " " + PREFIX_ANSWER + VALID_ANSWER_CODE;

    public static final String INVALID_QUESTION_DESC = " " + PREFIX_QUESTION + " "; // ' ' not allowed in questions
    public static final String INVALID_ANSWER_DESC = " " + PREFIX_ANSWER + "True"; // 'True' not allowed in answers

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditDeckCommand.EditDeckDescriptor DESC_DJANGO;
    public static final EditDeckCommand.EditDeckDescriptor DESC_REACT;

    public static final EditCardCommand.EditCardDescriptor DESC_QUESTION1;
    public static final EditCardCommand.EditCardDescriptor DESC_QUESTION2;

    static {
        DESC_DJANGO = new EditDeckDescriptorBuilder().withTitle(VALID_TITLE_DJANGO)
                .withTags(VALID_TAG_FRONTEND).build();
        DESC_REACT = new EditDeckDescriptorBuilder().withTitle(VALID_TITLE_REACT)
                .withTags(VALID_TAG_HARD, VALID_TAG_FRONTEND).build();
        DESC_QUESTION1 = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_MODULECODE)
                .withAnswer(VALID_ANSWER_CODE).build();
        DESC_QUESTION2 = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_MODULECODE2)
                .withAnswer(VALID_ANSWER_CODE2).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel, CommandHistory commandHistory) {
        try {
            CommandResult result = command.execute(actualModel, commandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel, CommandHistory commandHistory) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel, commandHistory);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered deck list and selected deck in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory commandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        CardiBuddy expectedCardiBuddy = new CardiBuddy(actualModel.getCardiBuddy());
        List<Deck> expectedFilteredList = new ArrayList<>(actualModel.getFilteredDeckList());

        //assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel, commandHistory));
        assertEquals(expectedCardiBuddy, actualModel.getCardiBuddy());
        assertEquals(expectedFilteredList, actualModel.getFilteredDeckList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the deck at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showDeckAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDeckList().size());

        Deck deck = model.getFilteredDeckList().get(targetIndex.getZeroBased());
        final String[] splitName = deck.getTitle().toString().split("\\s+");
        model.updateFilteredDeckList(new SearchDeckKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDeckList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the deck at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showCardAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFlashcardList().size());

        Card card = model.getFilteredFlashcardList().get(targetIndex.getZeroBased());
        final String[] splitName = card.getQuestion().toString().split("\\s+");
        model.updateFilteredFlashcardList(new SearchCardKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredFlashcardList().size());
    }
}
