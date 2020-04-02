package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cardibuddy.commons.core.Messages.MESSAGE_NOT_IN_DECK;

import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.DeleteCardCommand;
import cardibuddy.logic.commands.DeleteCommand;
import cardibuddy.logic.commands.DeleteDeckCommand;
import cardibuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private LogicToUiManager logicToUiManager;

    public DeleteCommandParser(LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            if (args.substring(1, 5).equals("deck")) {
                Index index = ParserUtil.parseIndex(args.substring(5));
                logicToUiManager.setOpenedDeck(null);
                logicToUiManager.openDeckPanel();
                return new DeleteDeckCommand(index, logicToUiManager);
            } else if (args.substring(1, 5).equals("card")) {
                if (!logicToUiManager.isInDeck()) {
                    throw new ParseException(String.format(MESSAGE_NOT_IN_DECK));
                }
                Index index = ParserUtil.parseIndex(args.substring(5));
                return new DeleteCardCommand(index, logicToUiManager);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
