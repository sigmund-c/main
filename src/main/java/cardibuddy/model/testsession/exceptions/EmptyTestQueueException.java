package cardibuddy.model.testsession.exceptions;

import java.util.NoSuchElementException;

/**
 * An exception for TestSession.
 * Thrown when trying to access a question from the TestSession,
 * when the {@code testQueue} is already empty.
 */
public class EmptyTestQueueException extends NoSuchElementException {
}
