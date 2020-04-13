package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.DeleteCommand;
import cardibuddy.logic.commands.DeleteDeckCommand;
import cardibuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteDeckCommand object.
 */
public class DeleteDeckCommandParser implements Parser<DeleteDeckCommand> {

    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "deck : Deletes a deck identified by the index number used in the displayed panel.\n"
            + "Parameters: DECK_INDEX (must be a positive and valid integer)\n"
            + "Example: " + COMMAND_WORD + " deck 1 \n";

    private LogicToUiManager logicToUiManager;
    public DeleteDeckCommandParser(LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteDeckCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            logicToUiManager.setOpenedDeck(null);
            logicToUiManager.openDeckPanel();
            return new DeleteDeckCommand(index, logicToUiManager);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
