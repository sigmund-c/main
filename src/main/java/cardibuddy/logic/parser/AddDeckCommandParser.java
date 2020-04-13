package cardibuddy.logic.parser;

import static cardibuddy.logic.parser.CliSyntax.PREFIX_DECK;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cardibuddy.logic.commands.AddDeckCommand;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddDeckCommand object.
 */
public class AddDeckCommandParser implements Parser<AddDeckCommand> {

    public static final String MESSAGE_ADD_DECK = "To add a deck to the cardibuddy book. \n"
            + "Parameters: "
            + PREFIX_DECK + "DECK_TITLE "
            + "[" + PREFIX_TAG + "TAG]... \n"
            + "Example: add "
            + PREFIX_DECK + "CS2103T "
            + PREFIX_TAG + "Hard "
            + PREFIX_TAG + "Software Engineering";

    @Override
    public AddDeckCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DECK, PREFIX_TAG);

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_DECK).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        List<Card> flashcards = new ArrayList<>();

        return new AddDeckCommand(new Deck(title, tagList, flashcards));
    }
}
