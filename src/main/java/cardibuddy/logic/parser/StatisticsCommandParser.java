package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.StatisticsCommand;
import cardibuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new StatisticsCommand object
 */
public class StatisticsCommandParser implements Parser<StatisticsCommand> {

    private static Logger logger = Logger.getLogger(StatisticsCommand.COMMAND_WORD);
    private LogicToUiManager logicToUiManager;

    public StatisticsCommandParser(LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the StatisticsCommand
     * and returns a StatisticsCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatisticsCommand parse(String args) throws ParseException {
        Index deckIndex;
        Index sessionIndex;

        String trimmedArgs = args.trim();
        String[] splitArgs = trimmedArgs.split(" ");

        if (splitArgs.length == 1) {
            if (splitArgs[0].isEmpty()) {
                return new StatisticsCommand(logicToUiManager);
            }
            try {
                deckIndex = ParserUtil.parseIndex(splitArgs[0]);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE), pe);
            }

            return new StatisticsCommand(deckIndex, logicToUiManager);
        } else if (splitArgs.length == 2) {
            try {
                deckIndex = ParserUtil.parseIndex(splitArgs[0]);
                sessionIndex = ParserUtil.parseIndex(splitArgs[1]);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE), pe);
            }

            return new StatisticsCommand(deckIndex, sessionIndex, logicToUiManager);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
        }

    }

}
