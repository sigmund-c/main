package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.TestCommand;
import cardibuddy.logic.parser.exceptions.ParseException;


/**
 * A parser for the test command, used to initiate a test session.
 */
public class TestCommandParser implements Parser<TestCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AnswerCommand
     * and returns a AnswerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */

    private static Logger logger = Logger.getLogger(TestCommand.COMMAND_WORD);
    private LogicToUiManager logicToUiManager;

    TestCommandParser(LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Parses the given {@code String} of arguments for initiating a TestSession,
     * and returns a AnswerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format (eg. nonexistent index)
     */
    public TestCommand parse(String args) throws ParseException {
        try { // TODO: figure out how to parse additional test settings along with the index
            Index index = ParserUtil.parseIndex(args);
            return new TestCommand(index, logicToUiManager);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestCommand.MESSAGE_USAGE), pe);
        }
    }
}
