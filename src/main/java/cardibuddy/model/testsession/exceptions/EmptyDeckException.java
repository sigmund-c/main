package cardibuddy.model.testsession.exceptions;

import cardibuddy.commons.core.Messages;

/**
 * Test Exception (add more details).
 */
public class EmptyDeckException extends Exception{
    public EmptyDeckException() {
        super(Messages.MESSAGE_EMPTY_DECK);
    }
}
