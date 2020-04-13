package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.*;
import static cardibuddy.logic.parser.CliSyntax.*;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_ANSWER;
import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.logging.Logger;

import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.EditCardCommand;
import cardibuddy.logic.commands.EditCommand;
import cardibuddy.logic.commands.EditDeckCommand;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.Question;
import javafx.collections.ObservableList;

/**
 * Parses input arguments and creates a new EditCardCommand object
 */
public class EditCardCommandParser implements Parser<EditCardCommand> {

    private LogicToUiManager logicToUiManager;

    public EditCardCommandParser(LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCardCommand
     * and returns an EditCardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCardCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUESTION, PREFIX_ANSWER);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE), pe);
        }

        EditCardCommand.EditCardDescriptor editCardDescriptor = new EditCardCommand.EditCardDescriptor();

        parseQuestionForEdit(argMultimap.getValue(PREFIX_QUESTION).get()).ifPresent(editCardDescriptor::setQuestion);
        parseAnswerForEdit(argMultimap.getValue(PREFIX_ANSWER).get()).ifPresent(editCardDescriptor::setAnswer);

        if (!editCardDescriptor.isFieldEdited()) {
            throw new ParseException(EditCardCommand.MESSAGE_NOT_EDITED);
        }
        return new EditCardCommand(index, editCardDescriptor, logicToUiManager);
    }

    /**
     * Parses the string into a Question object if question is non-empty.
     * @param question string from user input.
     * @return an optional object.
     * @throws ParseException if there is an error.
     */
    private Optional<Question> parseQuestionForEdit(String question) throws ParseException {
        assert question != null;

        if (question.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(ParserUtil.parseQuestion(question));
    }

    /**
     * Parses the string into an Answer object if question is non-empty.
     * @param answer string from user input.
     * @return an optional object.
     * @throws ParseException if there is an error.
     */
    private Optional<Answer> parseAnswerForEdit(String answer) throws ParseException {
        assert answer != null;

        if (answer.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(ParserUtil.parseAnswer(answer));
    }
}
