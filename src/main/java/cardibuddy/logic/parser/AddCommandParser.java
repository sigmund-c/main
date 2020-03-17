package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.MESSAGE_DECK_CANNOT_BE_CARD;
import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_DECK;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_FLASHCARD;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_QUESTION;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_ANSWER;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import cardibuddy.logic.commands.AddCommand;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.deck.exceptions.DeckCannotBeCardException;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    private static Object toAdd;

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DECK, PREFIX_FLASHCARD, PREFIX_TAG,
                        + PREFIX_QUESTION, PREFIX_ANSWER);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_DECK, PREFIX_FLASHCARD)) {
            // both PREFIX_DECK and PREFIX_FLASHCARD are present
            throw new DeckCannotBeCardException(String.format(MESSAGE_DECK_CANNOT_BE_CARD, AddCommand.MESSAGE_USAGE));
        }

        if (argMultimap.containsKey(PREFIX_DECK)) {
            Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_DECK).get());
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            toAdd = new Deck(title, tagList);
            return new AddCommand((Deck) toAdd);
        } else if (argMultimap.containsKey(PREFIX_FLASHCARD))  {
            Title title = new Title("");
            Set<Tag> tagList = Collections.<Tag>emptySet();
            Deck deck = new Deck(title, tagList);
            Question question = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
            Answer answer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get());
            toAdd = new Flashcard(deck, question, answer);
            return new AddCommand((Flashcard) toAdd);
        }
        return null;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
