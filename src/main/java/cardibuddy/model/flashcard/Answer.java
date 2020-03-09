package cardibuddy.model.flashcard;

public interface Answer {

    boolean isValid(String test);

    boolean checkAnswer(String toCheck);

    String getCorrectAnswer();
}
