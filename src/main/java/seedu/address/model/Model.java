package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' cardibuddy book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' cardibuddy book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces cardibuddy book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a deck with the same identity as {@code deck} exists in the cardibuddy book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given deck.
     * The deck must exist in the cardibuddy book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given deck.
     * {@code deck} must not already exist in the cardibuddy book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given deck {@code target} with {@code editedPerson}.
     * {@code target} must exist in the cardibuddy book.
     * The deck identity of {@code editedPerson} must not be the same as another existing deck in the cardibuddy book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered deck list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered deck list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
}
