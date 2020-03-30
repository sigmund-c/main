package cardibuddy.model.testsession.exceptions;

import cardibuddy.commons.core.Messages;

import java.util.NoSuchElementException;

/**
 * Test Exception (add more details).
 */
public class EmptyDeckException extends NoSuchElementException {
    public EmptyDeckException() {
        super(Messages.MESSAGE_EMPTY_DECK);
    }
}
