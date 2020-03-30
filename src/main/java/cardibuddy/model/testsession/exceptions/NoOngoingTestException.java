package cardibuddy.model.testsession.exceptions;

/**
 * Called when trying to access a TestSession that is not ongoing.
 */
public class NoOngoingTestException extends NullPointerException {
    public NoOngoingTestException() {
    }
}
