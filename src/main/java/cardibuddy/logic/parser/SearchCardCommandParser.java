package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.SearchCardCommand;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.flashcard.SearchCardKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCardCommand object.
 */
public class SearchCardCommandParser implements Parser<SearchCardCommand> {

    private LogicToUiManager logicToUiManager;

    public SearchCardCommandParser(LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCardCommand
     * and returns a SearchCardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCardCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCardCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SearchCardCommand(new SearchCardKeywordsPredicate(Arrays.asList(nameKeywords)), logicToUiManager);
    }

}

