package cardibuddy.logic.commands;

import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;

/**
 * Represents an add command to be extended into add deck and add card exceptions.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    protected static final Logger LOGGER = LogsCenter.getLogger(AddCommand.class);

    public abstract boolean equals(Object other);
}
