package cardibuddy.logic.commands;

/**
 * Represents a search command to be extended into search deck and search card exceptions.
 */
public abstract class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public abstract boolean equals(Object other);

}
