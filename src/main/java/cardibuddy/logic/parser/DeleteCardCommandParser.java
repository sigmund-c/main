package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cardibuddy.commons.core.Messages.MESSAGE_NOT_IN_DECK;

import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.DeleteCardCommand;
import cardibuddy.logic.commands.DeleteCommand;
import cardibuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCardCommand object.
 */
public class DeleteCardCommandParser implements Parser<DeleteCardCommand> {

    private LogicToUiManager logicToUiManager;

    public DeleteCardCommandParser(LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCardCommand parse(String args) throws ParseException {
        try {
            if (!logicToUiManager.isInDeck()) {
                throw new ParseException(String.format(MESSAGE_NOT_IN_DECK));
            } else {
                Index index = ParserUtil.parseIndex(args);
                return new DeleteCardCommand(index, logicToUiManager);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
