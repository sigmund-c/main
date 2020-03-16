package cardibuddy.model.flashcard;

/**
 * API of Answer Component.
 */
public interface Answer {

    boolean isValid(String test);

    boolean checkAnswer(String toCheck);

    String getCorrectAnswer();
}
