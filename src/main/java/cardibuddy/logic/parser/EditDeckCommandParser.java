package cardibuddy.logic.parser;
import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_DECK;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.commands.EditCommand;
import cardibuddy.logic.commands.EditDeckCommand;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.deck.Title;
import cardibuddy.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditDeckCommand object
 */
public class EditDeckCommandParser implements Parser<EditDeckCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditDeckCommand
     * and returns an EditDeckCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditDeckCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DECK, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDeckCommand.MESSAGE_USAGE), pe);
        }

        EditDeckCommand.EditDeckDescriptor editDeckDescriptor = new EditDeckCommand.EditDeckDescriptor();

        if (argMultimap.getValue(PREFIX_DECK).isPresent() && !argMultimap.getValue(PREFIX_DECK)
                .equals(editDeckDescriptor.getTitle())) {
            parseTitleForEdit(argMultimap.getValue(PREFIX_DECK).get()).ifPresent(editDeckDescriptor::setTitle);
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent() && !argMultimap.getValue(PREFIX_TAG)
                .equals(editDeckDescriptor.getTags())) {
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editDeckDescriptor::setTags);
        }

        if (!editDeckDescriptor.isFieldEdited()) {
            String errorMsg = EditCommand.MESSAGE_USAGE + EditDeckCommand.MESSAGE_USAGE;
            throw new ParseException(errorMsg);
        }

        return new EditDeckCommand(index, editDeckDescriptor);
    }

    /**
     * Parses the string into a Title object if title is non-empty.
     * @param title string from user input.
     * @return an optional object.
     * @throws ParseException if there is an error.
     */
    private Optional<Title> parseTitleForEdit(String title) throws ParseException {
        assert title != null;

        if (title.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(ParserUtil.parseTitle(title));
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
