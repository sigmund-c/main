package cardibuddy.logic.commands;

import static cardibuddy.commons.core.Messages.MESSAGE_TEST_ONGOING;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_ANSWER;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_QUESTION;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;


/**
 * Edits Card {@code Question} or {@code Answer}.
 */
public class EditCardCommand extends EditCommand {

    public static final String COMMAND_WORD = "card";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_QUESTION + "QUESTION"
            + PREFIX_ANSWER + "ANSWER\n"
            + "Example: edit card 1 q/What does UML stand for?";

    public static final String MESSAGE_EDIT_CARD_SUCCESS = "Edited card" + ": %1$s";
    public static final String MESSAGE_NOT_EDITED = "All fields to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CARD = "This card already exists in the deck.";

    private final Index index;
    private final EditCardDescriptor editCardDescriptor;
    private LogicToUiManager logicToUiManager;

    /**
     * @param index of the deck in the filtered deck list to edit
     * @param editCardDescriptor details to edit the deck with
     */
    public EditCardCommand(Index index, EditCardDescriptor editCardDescriptor, LogicToUiManager logicToUiManager) {
        requireNonNull(index);
        requireNonNull(editCardDescriptor);

        this.index = index;
        this.logicToUiManager = logicToUiManager;
        this.editCardDescriptor = new EditCardDescriptor(editCardDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        if (model.hasOngoingTestSession()) {
            throw new CommandException(MESSAGE_TEST_ONGOING);
        }

        List<Card> lastShownList = logicToUiManager.getDisplayedDeck().getFilteredFlashcardList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        Card cardToEdit = lastShownList.get(index.getZeroBased());
        Card editedCard = createEditedCard(cardToEdit, editCardDescriptor);

        if (logicToUiManager.getDisplayedDeck().hasFlashcard(editedCard)) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

        logicToUiManager.getDisplayedDeck().setFlashcard(cardToEdit, editedCard);
        logicToUiManager.updateFlashcardPanel();
        model.commitCardiBuddy();
        return new CommandResult(String.format(MESSAGE_EDIT_CARD_SUCCESS, editedCard));
    }

    /**
     * Creates and returns a {@code Card} with the details of {@code cardToEdit}
     * edited with {@code editCardDescriptor}.
     */
    private static Card createEditedCard(Card cardToEdit, EditCardDescriptor editCardDescriptor) {
        assert cardToEdit != null;

        Deck updatedDeck = cardToEdit.getDeck();
        Question updatedQuestion = editCardDescriptor.getQuestion().orElse(cardToEdit.getQuestion());
        Answer updatedAnswer = editCardDescriptor.getAnswer().orElse(cardToEdit.getAnswer());
        String updatedPath = cardToEdit.getPath();

        return new Flashcard(updatedDeck, updatedQuestion, updatedAnswer, updatedPath);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCardCommand)) {
            return false;
        }

        // state check
        EditCardCommand e = (EditCardCommand) other;
        return index.equals(e.index)
                && editCardDescriptor.equals(e.editCardDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditCardDescriptor {
        private Deck deck;
        private Question question;
        private Answer answer;
        private String path;

        public EditCardDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of the parameters are used internally.
         */
        public EditCardDescriptor(EditCardDescriptor toCopy) {
            setQuestion(toCopy.question);
            setAnswer(toCopy.answer);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isFieldEdited() {
            return !(question == null) || !(answer == null);
        }

        /**
         * Sets {@code question} to this object's {@code question}.
         * A defensive copy of {@code question} is used internally.
         */
        public void setQuestion(Question question) {
            this.question = question;
        }

        /**
         * Sets {@code answer} to this object's {@code answer}.
         * A defensive copy of {@code answer} is used internally.
         */
        public void setAnswer(Answer answer) {
            this.answer = answer;
        }

        /**
         * Returns an unmodifiable Question, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code question} is null.
         */
        public Optional<Question> getQuestion() {
            return (question != null) ? Optional.of(question) : Optional.empty();
        }

        /**
         * Sets {@code deck} to this object's {@code deck}.
         * A defensive copy of {@code deck} is used internally.
         */
        public void getDeck(Deck deck) {
            this.deck = deck;
        }

        /**
         * Sets {@code path} to this object's {@code path}.
         * A defensive copy of {@code path} is used internally.
         */
        public void getPath(String path) {
            this.path = path;
        }

        /**
         * Returns an unmodifiable Answer, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code answer} is null.
         */
        public Optional<Answer> getAnswer() {
            return (answer != null) ? Optional.of(answer) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCardDescriptor)) {
                return false;
            }

            // state check
            EditCardDescriptor e = (EditCardDescriptor) other;

            return getQuestion().equals(e.getQuestion()) && getAnswer().equals(e.getAnswer());
        }
    }
}

