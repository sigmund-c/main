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
