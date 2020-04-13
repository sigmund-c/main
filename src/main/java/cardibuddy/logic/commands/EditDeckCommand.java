package cardibuddy.logic.commands;

import static cardibuddy.commons.core.Messages.MESSAGE_TEST_ONGOING;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_DECK;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static cardibuddy.model.Model.PREDICATE_SHOW_ALL_DECKS;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
import cardibuddy.commons.util.CollectionUtil;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.tag.Tag;

/**
 * Edits Deck {@code Title} or its {@code Tags}.
 */
public class EditDeckCommand extends EditCommand {

    public static final String COMMAND_WORD = "deck";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_DECK + "DECK_TITLE "
            + PREFIX_TAG + "TAG... or t/ to clear all existing tags\n"
            + "Example: edit deck 1 t/Hard";

    public static final String MESSAGE_EDIT_DECK_SUCCESS = "Edited deck" + ": %1$s";
    public static final String MESSAGE_NOT_EDITED = "All fields to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_DECK = "This deck already exists in the cardibuddy library.";

    private final Index index;
    private final EditDeckDescriptor editDeckDescriptor;

    /**
     * @param index of the deck in the filtered deck list to edit
     * @param editDeckDescriptor details to edit the deck with
     */
    public EditDeckCommand(Index index, EditDeckDescriptor editDeckDescriptor) {
        requireNonNull(index);
        requireNonNull(editDeckDescriptor);

        this.index = index;
        this.editDeckDescriptor = new EditDeckDescriptor(editDeckDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        if (model.hasOngoingTestSession()) {
            throw new CommandException(MESSAGE_TEST_ONGOING);
        }
        List<Deck> lastShownList = model.getFilteredDeckList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }

        Deck deckToEdit = lastShownList.get(index.getZeroBased());
        Deck editedDeck = createEditedDeck(deckToEdit, editDeckDescriptor);

        if (!deckToEdit.isSameDeck(editedDeck) && model.hasDeck(editedDeck)) {
            throw new CommandException(MESSAGE_DUPLICATE_DECK);
        }

        model.setDeck(deckToEdit, editedDeck);
        model.updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
        model.commitCardiBuddy();
        return new CommandResult(String.format(MESSAGE_EDIT_DECK_SUCCESS, editedDeck));
    }

    /**
     * Creates and returns a {@code Deck} with the details of {@code deckToEdit}
     * edited with {@code editDeckDescriptor}.
     */
    private static Deck createEditedDeck(Deck deckToEdit, EditDeckDescriptor editDeckDescriptor) {
        assert deckToEdit != null;

        Title updatedTitle = editDeckDescriptor.getTitle().orElse(deckToEdit.getTitle());
        Set<Tag> updatedTags = editDeckDescriptor.getTags().orElse(deckToEdit.getTags());

        return new Deck(updatedTitle, updatedTags, deckToEdit.getFlashcards());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditDeckCommand)) {
            return false;
        }

        // state check
        EditDeckCommand e = (EditDeckCommand) other;
        return index.equals(e.index)
                && editDeckDescriptor.equals(e.editDeckDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditDeckDescriptor {
        private Set<Tag> tags;
        private Title title;

        public EditDeckDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDeckDescriptor(EditDeckDescriptor toCopy) {
            setTags(toCopy.tags);
            setTitle(toCopy.title);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isFieldEdited() {
            return !(title == null) || CollectionUtil.isAnyNonNull(tags);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return (title != null) ? Optional.of(title) : Optional.empty();
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDeckDescriptor)) {
                return false;
            }

            // state check
            EditDeckDescriptor e = (EditDeckDescriptor) other;

            return getTitle().equals(e.getTitle()) && getTags().equals(e.getTags());
        }
    }
}
