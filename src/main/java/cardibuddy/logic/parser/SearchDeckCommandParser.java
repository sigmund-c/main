package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import cardibuddy.logic.commands.SearchDeckCommand;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.deck.SearchDeckKeywordsPredicate;


/**
 * Parses input arguments and creates a new SearchDeckCommand object.
 */
public class SearchDeckCommandParser implements Parser<SearchDeckCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchDeckCommand
     * and returns a SearchDeckCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchDeckCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchDeckCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SearchDeckCommand(new SearchDeckKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
