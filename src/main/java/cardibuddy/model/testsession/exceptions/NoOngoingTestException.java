package cardibuddy.model.testsession.exceptions;

import cardibuddy.commons.core.Messages;

/**
 * Called when trying to access a TestSession that is not ongoing.
 */
public class NoOngoingTestException extends NullPointerException{
    public NoOngoingTestException() {
    }
}
