package cardibuddy.model.testsession.exceptions;

import java.util.NoSuchElementException;

import cardibuddy.commons.core.Messages;

/**
 * Test Exception (add more details).
 */
public class EmptyDeckException extends NoSuchElementException {
    public EmptyDeckException() {
        super(Messages.MESSAGE_EMPTY_DECK);
    }
}
