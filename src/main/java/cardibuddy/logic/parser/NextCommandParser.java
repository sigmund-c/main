package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.NextCommand;
import cardibuddy.logic.parser.exceptions.ParseException;

import java.util.logging.Logger;


/**
 * A parser for the test command, used to initiate a test session.
 */
public class NextCommandParser implements Parser<NextCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AnswerCommand
     * and returns a AnswerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */

    private static Logger logger = Logger.getLogger(NextCommand.COMMAND_WORD);
    private LogicToUiManager logicToUiManager;

    NextCommandParser(LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Parses the given {@code String} of arguments for initiating a TestSession,
     * and returns a AnswerCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format (eg. nonexistent index)
     */
    public NextCommand parse(String args) throws ParseException {
        if (!args.equals("") && !args.equals("force")) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
        return new NextCommand(logicToUiManager, args);
    }
}
