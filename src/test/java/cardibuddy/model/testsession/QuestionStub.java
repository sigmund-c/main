package cardibuddy.model.testsession;

import cardibuddy.model.flashcard.Question;

public class QuestionStub extends Question {
    String question;
    public QuestionStub(String questionString) {
        super(questionString);
        question = questionString;
    }

    @Override
    public String toString() {
        return question;
    }
}
