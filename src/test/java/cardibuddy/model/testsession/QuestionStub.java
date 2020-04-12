package cardibuddy.model.testsession;

import cardibuddy.model.flashcard.Question;

/**
 * Stub class for Question.
 */
public class QuestionStub extends Question {
    private String question;
    public QuestionStub(String questionString) {
        super(questionString);
        question = questionString;
    }

    @Override
    public String toString() {
        return question;
    }
}
